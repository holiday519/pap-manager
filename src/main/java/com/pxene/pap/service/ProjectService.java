package com.pxene.pap.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
/*import org.apache.log4j.Logger;*/
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.beans.ProjectBean.EffectField;
import com.pxene.pap.domain.configs.ConmonConfigHelp;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.EffectDicModel;
import com.pxene.pap.domain.models.EffectDicModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.EffectDicDao;
import com.pxene.pap.repository.basic.IndustryDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;

import redis.clients.jedis.Jedis; 

@Service
public class ProjectService extends BaseService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private CreativeService creativeService;
	
	@Autowired
	private IndustryDao industryDao;
	
	@Autowired
	private EffectDicDao effectDicDao;
	
	@Autowired
	private QuantityDao quantityDao;
	
	@Autowired
	private LaunchService launchService;

    private RedisHelper redisHelper;

    private String excelSavePath= ConmonConfigHelp.EXCEL_SAVEPATH;

    public ProjectService() {
        redisHelper = RedisHelper.open("redis.primary.");
    }
	
	/**
	 * 创建项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
    public void createProject(ProjectBean bean) throws Exception
    {
        // 验证项目名称是否已存在
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andNameEqualTo(bean.getName());
        List<ProjectModel> projects = projectDao.selectByExample(example);
        if (projects != null && !projects.isEmpty())
        {
            throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
        }
        
        // 查询广告主是否存在
        String advertiserId = bean.getAdvertiserId();
        AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
        if (advertiser == null) {
        	throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
        }
        
        // 将项目信息插入MySQL
        ProjectModel project = modelMapper.map(bean, ProjectModel.class);
        project.setId(UUIDGenerator.getUUID());
        project.setStatus(StatusConstant.PROJECT_PROCEED);
        projectDao.insertSelective(project);
        
        // 复制置设好的属性回请求对象中
        BeanUtils.copyProperties(project, bean);
        
        // 将项目总预算插入Redis
        String projectBudgetKey = RedisKeyConstant.PROJECT_BUDGET + project.getId();
        Integer totalBudget = project.getTotalBudget();
        totalBudget = totalBudget * 100; // 将元转换成分
        redisHelper.setNX(projectBudgetKey, totalBudget);
        
        // 初始化转化字段
        initEffectField(bean.getId());

		//初始化模板文件
		boolean res = createTransformExcel(bean.getId(),null);
		if (!res) {
			throw new IllegalStatusException("生成excel文件失败:projectId="+bean.getId());
		}
    }

	/**
	 * 编辑项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
    public void updateProject(String id, ProjectBean bean) throws Exception
    {
	    // 判断指定ID的项目在MySQL中是否存在
        ProjectModel projectInDB = projectDao.selectByPrimaryKey(id);
        if (projectInDB == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        else
        {
            String nameInDB = projectInDB.getName(); // 数据库中的项目名
            String name = bean.getName();            // 欲修改成的项目名
            
            if (!nameInDB.equals(name))
            {
                ProjectModelExample projectExample = new ProjectModelExample();
                projectExample.createCriteria().andNameEqualTo(name);
                
                // 如果数据库中其它项目的名称与欲修改成的项目名称重复，则禁止修改
                List<ProjectModel> projects = projectDao.selectByExample(projectExample);
                if (projects != null & !projects.isEmpty())
                {
                    throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
                }
            }
        }
        
        // 查询广告主是否存在
        String advertiserId = bean.getAdvertiserId();
        AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
        if (advertiser == null) {
        	throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
        }
        
        // 修改Reids中保存的项目总预算
//        if (projectInDB.getTotalBudget().compareTo(bean.getTotalBudget()) != 0)
//        {
//            changeBudgetInRedis(id, bean.getTotalBudget());
//        }
        int oldBudget = projectInDB.getTotalBudget();
        int newBudget = bean.getTotalBudget();
        // 判断该预算是否大于活动日预算
        CampaignModelExample campaignExample = new CampaignModelExample();
        campaignExample.createCriteria().andProjectIdEqualTo(id);
        List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
        for (CampaignModel campaign : campaigns) {
        	QuantityModelExample quantityExample = new QuantityModelExample();
        	quantityExample.createCriteria().andCampaignIdEqualTo(campaign.getId());
        	List<QuantityModel> quantities = quantityDao.selectByExample(quantityExample);
        	for (QuantityModel quantity : quantities) {
        		Integer dailyBudget = quantity.getDailyBudget();
        		if (dailyBudget != null && dailyBudget > newBudget) {
        			throw new IllegalArgumentException(PhrasesConstant.PROJECT_BUDGET_UNDER_CAMPAIGN);
        		}
        	}
        }
        
        changeBudgetInRedis(id, oldBudget, newBudget);
        
        // 将请求参数转换成MyBatis Model
        ProjectModel model = modelMapper.map(bean, ProjectModel.class);
        model.setId(id);
        
        // 更新MySQL中的指定ID的项目
        projectDao.updateByPrimaryKeySelective(model);
    }
	
	/**
	 * 修改项目状态
	 * @param id
	 * @param map
	 */
	@Transactional
	public void updateProjectStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("status"))) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		ProjectModel project = projectDao.selectByPrimaryKey(id);
		if (project == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		String status = map.get("status").toString();
		if (StatusConstant.PROJECT_PAUSE.equals(status)) {
			//暂停
			pauseProject(project);
		} else if (StatusConstant.PROJECT_PROCEED.equals(status)) {
			//投放
			proceedProject(project);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.PARAM_OUT_OF_RANGE);
		}
	}
	
	/**
	 * 删除项目
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional
    public void deleteProject(String id) throws Exception
    {
	    // 判断指定ID的项目在MySQL中是否存在
        ProjectModel projectInDB = projectDao.selectByPrimaryKey(id);
        if (projectInDB == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        // 查询项目下是否存在活动
        CampaignModelExample example = new CampaignModelExample();
        example.createCriteria().andProjectIdEqualTo(id);
        List<CampaignModel> list = campaignDao.selectByExample(example);
        if (list != null && !list.isEmpty())
        {
            throw new IllegalStatusException(PhrasesConstant.PROJECT_HAVE_CAMPAIGN);
        }
        
        // 从MySQL中删除指定ID的项目
        projectDao.deleteByPrimaryKey(id);
        
        // 从Redis中删除指定ID的项目总预算
        String projectBudgetKey = RedisKeyConstant.PROJECT_BUDGET + id;
        redisHelper.delete(projectBudgetKey);
        
        // 删除项目转化字段
        List<String> projectIds = new ArrayList<>();
        projectIds.add(id);
        destoryEffectField(projectIds);


		//删除对应的excel模板
		File excelFile= new File(ConmonConfigHelp.EXCEL_SAVEPATH+"/"+id+".xlsx");
		if(excelFile.exists()){
			boolean res =excelFile.delete();
			if(!res){
				throw new IllegalStatusException("删除excel文件失败：projectid="+id);
			}
		}
		
		launchService.removeProjectKey(id);
    }
	
	/**
	 * 批量删除项目
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Transactional
    public void deleteProjects(String[] ids) throws Exception
    {
		if(ids.length ==0){
			throw new IllegalArgumentException();
		}

	    // 判断指定的多个项目ID是否在MySQL中存在
        ProjectModelExample projectExample = new ProjectModelExample();
        projectExample.createCriteria().andIdIn(Arrays.asList(ids));
        
        List<ProjectModel> projectInDB = projectDao.selectByExample(projectExample);
        if (projectInDB == null || projectInDB.size() < ids.length)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        // 遍历每一个项目，如果任何一个项目下存在活动，则放弃整个删除
        for (String id : ids)
        {
            CampaignModelExample campaignExample = new CampaignModelExample();
            campaignExample.createCriteria().andProjectIdEqualTo(id);
            
            List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
            if (campaigns != null && !campaigns.isEmpty())
            {
                throw new IllegalStatusException(PhrasesConstant.PROJECT_HAVE_CAMPAIGN);
            }
        }
        
        // 从MySQL中批量删除指定ID的项目
        projectDao.deleteByExample(projectExample);
        
        // 从Redis中删除指定ID的项目总预算
        for (String id : ids)
        {
            redisHelper.delete(RedisKeyConstant.PROJECT_BUDGET + id);
        }
        
        // 删除项目转化字段
        destoryEffectField(Arrays.asList(ids));


		//删除对应的excel模板
		File excelFile;
		for (String id : ids) {
			excelFile = new File(ConmonConfigHelp.EXCEL_SAVEPATH + "/" + id + ".xlsx");
			if (excelFile.exists()) {
				boolean res =excelFile.delete();
				if(!res){
					throw new IllegalStatusException("生成excel文件失败:projectId="+id);
				}
			}
		}
		
		for (String id : ids) {
			launchService.removeProjectKey(id);
		}
    }

	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
	@Transactional
    public ProjectBean getProject(String id) throws Exception {
    	//从视图中查询项目所相关信息
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andIdEqualTo(id);
		ProjectModel project = projectDao.selectByPrimaryKey(id);
		if (project == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		ProjectBean projectBean = modelMapper.map(project, ProjectBean.class);
        getParam4Bean(projectBean);//查询属性，并放如结果中
        return projectBean;
    }
    
    /**
     * 查询项目列表
     * @param name
     * @return
     * @throws Exception
     */
	@Transactional
    public List<ProjectBean> listProjects(String name, Long beginTime, Long endTime, String advertiserId) throws Exception {
        // mysql 使用like关键字进行查询时，当参数包含下划线时，需要进行转义
    	if (!StringUtils.isEmpty(name) && name.contains("_"))
    	{
    	    name = name.replace("_", "\\_");
    	}
    	
    	ProjectModelExample example = new ProjectModelExample();
		if (!StringUtils.isEmpty(name) && StringUtils.isEmpty(advertiserId)) {
			example.createCriteria().andNameLike("%" + name + "%");
		} else if (!StringUtils.isEmpty(advertiserId) && StringUtils.isEmpty(name)) {
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		} else if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(advertiserId)) {
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andNameLike("%" + name + "%");
		}
		
		// 设置按更新时间降序排序
		example.setOrderByClause("create_time DESC");
		
		List<ProjectModel> projects = projectDao.selectByExample(example);
		List<ProjectBean> beans = new ArrayList<ProjectBean>();
		
//		if (projects == null || projects.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
		
		for (ProjectModel model : projects) {
			ProjectBean bean = modelMapper.map(model, ProjectBean.class);
			getParam4Bean(bean);//查询属性，并放如结果中
			
			if (beginTime != null && endTime != null) {
				//查询每个项目的投放信息
				getData(beginTime, endTime, bean);
			}
			
			beans.add(bean);
		}
    	return beans;
    }
    
    /**
     * 查询项目投放数据
     * @param beginTime
     * @param endTime
     * @param bean
     * @throws Exception
     */
    private void getData(Long beginTime, Long endTime,ProjectBean bean) throws Exception {
    	//查询活动
    	CampaignModelExample campaignExample = new CampaignModelExample();
    	campaignExample.createCriteria().andProjectIdEqualTo(bean.getId());
    	List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
    	BasicDataBean dataBean = new BasicDataBean();//在此处创建bean，并初始化各个参数，保证所有数据都能返回，即便都是零
    	dataService.formatBeanParams(dataBean);
    	dataService.formatBeanRate(dataBean);
    	if (campaigns != null && !campaigns.isEmpty()) {
    		List<String> campaignIds = new ArrayList<String>();
    		for (CampaignModel campaign : campaigns) {
    			campaignIds.add(campaign.getId());
    		}
    		CreativeModelExample cExample = new CreativeModelExample();
    		cExample.createCriteria().andCampaignIdIn(campaignIds);
    		List<CreativeModel> list = creativeDao.selectByExample(cExample);
    		List<String> creativeIds = new ArrayList<String>();
    		if (list != null && !list.isEmpty()) {
    			for (CreativeModel model : list) {
    				creativeIds.add(model.getId());
    			}
    		}
    		dataBean = creativeService.getCreativeDatas(creativeIds, beginTime, endTime);
    	}
    	bean.setImpressionAmount(dataBean.getImpressionAmount());
    	bean.setClickAmount(dataBean.getClickAmount());
    	bean.setTotalCost(dataBean.getTotalCost());
    	bean.setJumpAmount(dataBean.getJumpAmount());
    	bean.setImpressionCost(dataBean.getImpressionCost());
    	bean.setClickCost(dataBean.getClickCost());
    	bean.setClickRate(dataBean.getClickRate());
    	bean.setJumpCost(dataBean.getJumpCost());
    	
	}
    
    /**
     * 查询项目属性
     * @param bean
     * @throws Exception
     */
    private void getParam4Bean(ProjectBean bean) throws Exception {
    	String advertiserId = bean.getAdvertiserId();
    	if (!StringUtils.isEmpty(advertiserId)) {
    		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
    		if (advertiser != null) {
    			bean.setAdvertiserName(advertiser.getName());
    			String industryId = advertiser.getIndustryId();
    			IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
    			bean.setIndustryId(industryId);
				if (industryModel != null) {
					bean.setIndustryName(industryModel.getName());
    			}
    		}
    	}
    	String projectId = bean.getId();
    	EffectDicModelExample example = new EffectDicModelExample();
    	example.createCriteria().andProjectIdEqualTo(projectId);
		//排序
		example.setOrderByClause("substring(column_code,2)+0 ASC");
    	List<EffectDicModel> effectDics = effectDicDao.selectByExample(example);

    	if (effectDics != null) {
    		int len = effectDics.size();
        	EffectField[] effectFields = new EffectField[len];
        	for (int i=0; i<len; i++) {
        		EffectDicModel dic = effectDics.get(i);
        		EffectField field = new EffectField();
        		field.setId(dic.getId());
        		field.setName(dic.getColumnName());
        		field.setCode(dic.getColumnCode());
        		field.setEnable(dic.getEnable());
        		effectFields[i] = field;
        	}
        	bean.setEffectFields(effectFields);
    	}
    }
    
	/**
	 * 按照项目投放（项目状态开启）
	 * @param project
	 * @throws Exception
	 */
	@Transactional
	public void proceedProject(ProjectModel project) throws Exception {
		// 查询改项目下活动状态为开启状态的活动信息
		String projectId = project.getId();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				String campaignId = campaign.getId();
				if (StatusConstant.CAMPAIGN_PROCEED.equals(campaign.getStatus())
						&& campaignService.isOnLaunchDate(campaignId)) {
					// 如果活动状态为开启，并且在活动的投放时间里
					if (!launchService.isHaveLaunched(campaignId)) {
						// 如果campaignId还没有投放，则写入信息
						launchService.write4FirstTime(campaign);
					}
					/* if (campaignService.isOnTargetTime(campaignId)) { */
					if (campaignService.isOnTargetTime(campaignId) && launchService.notOverDailyBudget(campaignId)
							&& launchService.notOverDailyCounter(campaignId)) {
						// 如果在定向的时间里，将campaignId写入到redis的投放groups中
						// 活动没有超出每天的日预算并且日均最大展现未达到上限
						// launchService.writeCampaignId(campaignId);
						boolean writeResult = launchService.launchCampaignRepeatable(campaignId);
						if (!writeResult) {
							throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
						}
					}
				}
			}
		}
		// 项目投放之后修改状态
		project.setStatus(StatusConstant.PROJECT_PROCEED);
		projectDao.updateByPrimaryKeySelective(project);
	}
	
	/**
	 * 按照项目暂停
	 * @param project
	 * @throws Exception
	 */
	@Transactional
	public void pauseProject(ProjectModel project) throws Exception {
		// 查询改项目下活动状态为开启状态的活动信息
		String projectId = project.getId();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		// 从redis的groups中删除该项目下所有活动状态为开启的活动id
		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				//launchService.removeCampaignId(campaign.getId());
				// 将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
				boolean removeResult = launchService.pauseCampaignRepeatable(campaign.getId());
				if (!removeResult) {
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}
		// 项目暂停之后修改状态
		project.setStatus(StatusConstant.PROJECT_PAUSE);
		projectDao.updateByPrimaryKeySelective(project);
	}

	@Transactional
    public void changeEffectName(String fieldId, Map<String, String> map)
    {
		String name = map.get("name");
        if (StringUtils.isEmpty(fieldId) || StringUtils.isEmpty(name))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }
        if (name.length() > 100) {
        	throw new IllegalArgumentException(PhrasesConstant.LENGTH_ERROR_NAME);
        }
        
        EffectDicModel record = new EffectDicModel();
        record.setId(fieldId);
        record.setColumnName(name);
        effectDicDao.updateByPrimaryKeySelective(record);

		//生成excel模板文件
		boolean res = getEffectDataToCreateExcel(fieldId);
		if(!res){
			throw new IllegalStatusException("生成excel文件失败");
		}
    }

	@Transactional
    public void changeEffectEnable(String fieldId, Map<String, String> map)
    {
		String enable = map.get("enable");
        if (StringUtils.isEmpty(fieldId) || StringUtils.isEmpty(enable))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }

        EffectDicModel record = new EffectDicModel();
        record.setId(fieldId);
        record.setEnable(enable);
        effectDicDao.updateByPrimaryKeySelective(record);

		//生成excel模板文件
		boolean res = getEffectDataToCreateExcel(fieldId);
		if (!res) {
			throw new IllegalStatusException("生成excel文件失败");
		}
    }

	@Transactional
    public void changeProjectBudget(String id, Map<String, String> map)
    {
        String budgetStr = map.get("budget");
        
        if (StringUtils.isEmpty(budgetStr))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }
        if (!com.pxene.pap.common.StringUtils.isInteger(budgetStr)) {
        	throw new IllegalArgumentException(PhrasesConstant.ARGUMENT_FORMAT_INCORRECT);
        }
        
        // 判断项目是否存在
        ProjectModel projectInDB = projectDao.selectByPrimaryKey(id);
        if (projectInDB == null) {
        	throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
		int oldBudget = projectInDB.getTotalBudget();
        int newBudget = Integer.parseInt(budgetStr);
        
        // 判断该预算是否大于活动日预算
        CampaignModelExample campaignExample = new CampaignModelExample();
        campaignExample.createCriteria().andProjectIdEqualTo(id);
        List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
        for (CampaignModel campaign : campaigns) {
        	QuantityModelExample quantityExample = new QuantityModelExample();
        	quantityExample.createCriteria().andCampaignIdEqualTo(campaign.getId());
        	List<QuantityModel> quantities = quantityDao.selectByExample(quantityExample);
        	for (QuantityModel quantity : quantities) {
        		Integer dailyBudget = quantity.getDailyBudget();
        		if (dailyBudget != null && dailyBudget > newBudget) {
        			throw new IllegalArgumentException(PhrasesConstant.PROJECT_BUDGET_UNDER_CAMPAIGN);
        		}
        	}
        }
        
        
        // 修改Reids中保存的项目总预算
        changeBudgetInRedis(id, oldBudget, newBudget);
        
        // 将表单值更新回MySQL中
        ProjectModel projectModel = new ProjectModel();
        projectModel.setId(id);
        projectModel.setTotalBudget(newBudget);
        
        int effectedRows = projectDao.updateByPrimaryKeySelective(projectModel);
        if (effectedRows <= 0)
        {
            throw new ServerFailureException();
        }
    }
    
    /**
     * 修改Reids中保存的项目总预算
     * @param projectId 项目ID
     * @param formVal   欲修改成的项目总预算值（表单值）
     */
    private void changeBudgetInRedis(String projectId, int oldBudget, int newBudget)
    {
        String projectBudgetKey = RedisKeyConstant.PROJECT_BUDGET + projectId;
        
        // Redis中保存的项目剩余预算(分)
        Jedis jedis = redisHelper.getJedis();
        jedis.watch(projectBudgetKey);
        int redisBudget = Integer.parseInt(jedis.get(projectBudgetKey));
        
        // 已消耗掉的项目预算(分)
        int usedBudget = oldBudget * 100 - redisBudget;
        
        // 如果欲修改的预算值不足以支付已消耗掉的项目预算，则抛出异常
        if (newBudget * 100 < usedBudget)
        {
            throw new IllegalArgumentException(PhrasesConstant.DIF_TOTAL_BIGGER_REDIS);
        }
        
        // 将表单值减去已消耗掉的值更新回Redis中
        boolean casFlag = redisHelper.doTransaction(jedis, projectBudgetKey, String.valueOf(newBudget * 100 - usedBudget));
        if (!casFlag)
        {
            throw new ServerFailureException();
        }
    }

    /**
     * 创建A1~A10，十个转化字段
     * @param projectId    项目ID
     */
    private void initEffectField(String projectId)
    {
        EffectDicModel record = null;
        for (int i = 1; i <= 10; i++)
        {
            record = new EffectDicModel();
            record.setId(UUIDGenerator.getUUID());
            record.setProjectId(projectId);
            record.setColumnCode("A" + i);
            record.setColumnName("");
            record.setEnable(StatusConstant.EFFECT_STATUS_DISABLE);
            effectDicDao.insert(record);
        }
        
    }

    /**
     * 删除指定项目ID下的全部转化字段
     * @param projectIds    待操作的全部项目ID
     */
    private void destoryEffectField(List<String> projectIds)
    {
    	if(projectIds == null || projectIds.size()==0){
			throw new IllegalArgumentException();
		}
        EffectDicModelExample example = new EffectDicModelExample();
        example.createCriteria().andProjectIdIn(projectIds);
        effectDicDao.deleteByExample(example);
    }

	/**
	 * 根据filedId得到转换数据，并生成excel文件
	 * @param fieldId
     */
    public boolean getEffectDataToCreateExcel(String fieldId){
    	//根据effectDic id找到projectId
		EffectDicModelExample example = new EffectDicModelExample();
		example.createCriteria().andIdEqualTo(fieldId);
		List<EffectDicModel> effectDics = effectDicDao.selectByExample(example);
		if(effectDics!= null && effectDics.size()>0){
			EffectDicModel effectDicModel = effectDics.get(0);
			//根据projectId查找转化数据
			String projectId = effectDicModel.getProjectId();
			if(projectId !=null) {
				example.clear();
				effectDics.clear();
				example.createCriteria().andProjectIdEqualTo(projectId).andEnableEqualTo(StatusConstant.EFFECT_STATUS_ENABLE);
				example.setOrderByClause("substring(column_code,2)+0 ASC");
				effectDics = effectDicDao.selectByExample(example);
				boolean res = createTransformExcel(projectId, effectDics);
				return res;
			}
		}
		return false;
	}

    /**
     * 创建评分转化模板
     * @param fileName 文件名（excel后缀必须是xls,可不加;如果不加，程序会自动加上）
     * @param effectDics
     * @return
     */
	public  boolean createTransformExcel(String fileName, List<EffectDicModel> effectDics){

		//判断文件名后缀，如果不是.xls结尾，自动加上
		if(!fileName.endsWith(".xlsx")){
			fileName = fileName+".xlsx";
		}
		ExcelUtil<String> excelUtil = new ExcelUtil<String>();

		XSSFWorkbook workBook = new XSSFWorkbook();
		//设置sheetName
		String sheetName = "sheet0";
		XSSFSheet sheet = workBook.createSheet(sheetName);

		//将检测码这列设置为文本格式
		excelUtil.setColumStyleForText(workBook,sheet,1);

		int arraySize = 2;
		if(effectDics!=null){
			arraySize += effectDics.size();
		}
		String[] fisrtHeaders =new String[arraySize];
		fisrtHeaders[0]="_#_3000";
		fisrtHeaders[1]="_#_3000";
		String[] secondHeaders =new String[arraySize];
		secondHeaders[0]= "日期";
		secondHeaders[1]= "监测码";

		if(effectDics!=null) {
			int i =0;
			for (EffectDicModel effectDic_temp : effectDics) {
				fisrtHeaders[i + 2] = effectDic_temp.getColumnCode()+ "_#_3000";
				secondHeaders[i + 2] = effectDic_temp.getColumnName();
				i++;
			}
		}

		//设置表头
		excelUtil.setGenerateHeader(workBook,sheet,fisrtHeaders);
		//设置第二列表头
		excelUtil.setCoumlns(workBook,sheet,1,secondHeaders);

		//保存excel
		boolean res = excelUtil.writeExcelTolocal(workBook,excelSavePath,fileName);

		return res;
	}

}
