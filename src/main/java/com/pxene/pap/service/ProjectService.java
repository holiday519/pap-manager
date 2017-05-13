package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
/*import org.apache.log4j.Logger;*/
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ProjectBean;
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

import redis.clients.jedis.Jedis; 

@Service
public class ProjectService extends BaseService {
	
//	private static final String PROJECT_BUDGET_PREFIX = "pap_project_budget_";

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
	private LaunchService launchService;

    private RedisHelper redisHelper;
	
	
	public ProjectService()
    {
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
        redisHelper.setNX(projectBudgetKey, project.getTotalBudget());
        
        // 初始化转化字段
        initEffectField(bean.getId());
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
            throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
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
        if (projectInDB.getTotalBudget() != bean.getTotalBudget())
        {
            changeBudgetInRedis(id, bean.getTotalBudget());
        }
        
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
	 * @param projectId
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
    }
	
	/**
	 * 批量删除项目
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Transactional
    public void deleteProjects(String[] ids) throws Exception
    {
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
    }

	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
    public ProjectBean getProject(String id) throws Exception {
    	//从视图中查询项目所相关信息
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andIdEqualTo(id);
		ProjectModel project = projectDao.selectByPrimaryKey(id);
		if (project == null) {
			throw new ResourceNotFoundException();
		}
		ProjectBean projectBean = modelMapper.map(project, ProjectBean.class);
        getParamForBean(projectBean);//查询属性，并放如结果中
        return projectBean;
    }
    
    /**
     * 查询项目列表
     * @param name
     * @return
     * @throws Exception
     */
    public List<ProjectBean> selectProjects(String name, Long beginTime, Long endTime, String advertiserId) throws Exception {
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
		
		if (projects == null || projects.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (ProjectModel model : projects) {
			ProjectBean bean = modelMapper.map(model, ProjectBean.class);
			getParamForBean(bean);//查询属性，并放如结果中
			
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
     * @param projectId
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
    private void getParamForBean(ProjectBean bean) throws Exception {
    	
    	String advertiserId = bean.getAdvertiserId();
    	if (!StringUtils.isEmpty(advertiserId)) {
    		AdvertiserModel adv = advertiserDao.selectByPrimaryKey(advertiserId);
    		if (adv!=null) {
    			bean.setAdvertiserName(adv.getName());
    			String industryId = adv.getIndustryId();
    			IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
    			bean.setIndustryId(industryId);
				if (industryModel != null) {
					bean.setIndustryName(industryModel.getName());
    			}
    		}
    	}
    }
    
	/**
	 * 按照项目投放（项目状态开启）
	 * @param projectIds
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
					if (launchService.isFirstLaunch(campaignId)) {
						// 如果campaignId不在redis中说明是第一次投放改活动，则将投放的基本信息写入到redis中
						launchService.write4FirstTime(campaign);
					}
					/* if (campaignService.isOnTargetTime(campaignId)) { */
					if (campaignService.isOnTargetTime(campaignId) && launchService.dailyBudgetJudge(campaignId)
							&& launchService.dailyCounterJudge(campaignId)) {
						// 如果在定向的时间里，将campaignId写入到redis的投放groups中
						// 活动没有超出每天的日预算并且日均最大展现未达到上限
						// launchService.writeCampaignId(campaignId);
						boolean writeResult = launchService.launchCampaignRepeatable(campaignId);
						LOGGER.info("chaxunLaunchTrue." + campaignId);
						if (!writeResult) {
							LOGGER.info("chaxunLaunchFalse." + campaignId);
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
	 * @param projectIds
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
				// launchService.removeCampaignId(campaign.getId());
				// 将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
				boolean removeResult = launchService.pauseCampaignRepeatable(campaign.getId());
				LOGGER.info("chaxunPauseTrue." + campaign.getId());
				if (!removeResult) {
					LOGGER.info("chaxunPauseFalse." + campaign.getId());
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}
		// 项目暂停之后修改状态
		project.setStatus(StatusConstant.PROJECT_PAUSE);
		projectDao.updateByPrimaryKeySelective(project);
	}

	@Transactional
    public void changeEffectName(String projectId, Map<String, String> map)
    {
        String code = map.get("code");
        String name = map.get("name");
        
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(name))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }
        
        ProjectModel project = projectDao.selectByPrimaryKey(projectId);
        if (project == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        // 查询指定项目ID和转化字段code是否存在于DB中
        EffectDicModelExample example = new EffectDicModelExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andColumnCodeEqualTo(code);
        
        List<EffectDicModel> rows = effectDicDao.selectByExample(example);
        if (rows == null || rows.isEmpty())
        {
            throw new ResourceNotFoundException(PhrasesConstant.EFFECT_CODE_NOT_FOUND);
        }
        
        EffectDicModel record = new EffectDicModel();
        record.setColumnName(name);
        
        example.clear();
        example.createCriteria().andProjectIdEqualTo(projectId).andColumnCodeEqualTo(code);
        
        effectDicDao.updateByExampleSelective(record, example);
    }

    public void changeEffectStatus(String projectId, Map<String, String> map)
    {
        String code = map.get("code");
        String enable = map.get("enable");
        
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(enable))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }

        // 检查指定ID的项目是否存在
        ProjectModel project = projectDao.selectByPrimaryKey(projectId);
        if (project == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        // 查询指定项目ID和转化字段code是否存在于DB中
        EffectDicModelExample example = new EffectDicModelExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andColumnCodeEqualTo(code);
        
        List<EffectDicModel> rows = effectDicDao.selectByExample(example);
        if (rows == null || rows.isEmpty())
        {
            throw new ResourceNotFoundException(PhrasesConstant.EFFECT_CODE_NOT_FOUND);
        }
        
        // 更改转化字段状态
        EffectDicModel record = new EffectDicModel();
        record.setEnable(enable);
        
        example.clear();
        example.createCriteria().andProjectIdEqualTo(projectId);
        
        effectDicDao.updateByExampleSelective(record, example);
    }

    public void changeProjectBudget(String projectId, Map<String, String> map)
    {
        String budgetStr = map.get("budget");
        
        if (StringUtils.isEmpty(budgetStr))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }
        
        int formVal = Integer.parseInt(budgetStr);
        
        // 修改Reids中保存的项目总预算
        changeBudgetInRedis(projectId, formVal);
        
        // 将表单值更新回MySQL中
        ProjectModel projectModel = new ProjectModel();
        projectModel.setId(projectId);
        projectModel.setTotalBudget(formVal);
        
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
    private void changeBudgetInRedis(String projectId, int formVal)
    {
        String projectBudgetKey = RedisKeyConstant.PROJECT_BUDGET + projectId;
        
        // 如果项目不存在，则无必要再继续操作
        ProjectModel projectInDB = projectDao.selectByPrimaryKey(projectId);
        if (projectInDB == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        // MySQL中保存的项目总预算
        int mysqlVal = projectInDB.getTotalBudget();
        
        // Redis中保存的项目剩余预算
        Jedis jedis = redisHelper.getJedis();
        jedis.watch(projectBudgetKey);
        int redisVal = Integer.parseInt(jedis.get(projectBudgetKey));
        
        // 已消耗掉的项目预算
        int used = mysqlVal - redisVal;
        
        // 如果欲修改的预算值不足以支付已消耗掉的项目预算，则抛出异常
        if (formVal < used)
        {
            throw new IllegalArgumentException(PhrasesConstant.DIF_TOTAL_BIGGER_REDIS);
        }
        
        // 将表单值减去已消耗掉的值更新回Redis中
        boolean casFlag = redisHelper.doTransaction(jedis, projectBudgetKey, String.valueOf(formVal - used));
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
        EffectDicModelExample example = new EffectDicModelExample();
        example.createCriteria().andProjectIdIn(projectIds);
        effectDicDao.deleteByExample(example);
    }
	
}
