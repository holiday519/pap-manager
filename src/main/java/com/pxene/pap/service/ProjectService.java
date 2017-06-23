package com.pxene.pap.service;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.pxene.pap.domain.models.*;
import com.pxene.pap.repository.basic.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
/*import org.apache.log4j.Logger;*/
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.common.FormulaUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.ScriptUtils;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.beans.ProjectBean.EffectField;
import com.pxene.pap.domain.beans.RuleFormulasBean.Formulas;
import com.pxene.pap.domain.beans.RuleFormulasBean.Formulas.Staticvals;
import com.pxene.pap.domain.beans.RuleFormulasBean.Staticval;
import com.pxene.pap.domain.beans.StaticvalBean;
import com.pxene.pap.domain.beans.RuleFormulasBean;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.EffectDicModel;
import com.pxene.pap.domain.models.EffectDicModelExample;
import com.pxene.pap.domain.models.FormulaModel;
import com.pxene.pap.domain.models.FormulaModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import com.pxene.pap.domain.models.RuleModel;
import com.pxene.pap.domain.models.RuleModelExample;
import com.pxene.pap.domain.models.FormulaModel;
import com.pxene.pap.domain.models.FormulaModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.repository.basic.RuleDao;
import com.pxene.pap.repository.basic.FormulaDao;

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

	@Autowired
    private RedisHelper redisHelper;	

    private String excelSavePath;

	@Autowired
	private StaticvalDao staticvalDao;

	@Autowired
	private RuleDao ruleDao;

	@Autowired
	private FormulaDao formulaDao;

	@Autowired
	private LandpageService landpageService;

    @Autowired
    public ProjectService(Environment env)
    {
        excelSavePath = env.getProperty("pap.excel.savePath");
    }


    @PostConstruct
    public void selectRedis()
    {
        redisHelper.select("redis.primary.");
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
//        totalBudget = totalBudget * 100; // 将元转换成分
        redisHelper.setNX(projectBudgetKey, (int)totalBudget* 100); // 将元转换成分

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
		// 判断状态是否为空
		if (StringUtils.isEmpty(map.get("status"))) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 根据项目id查询项目信息，判断项目是否存在
		ProjectModel project = projectDao.selectByPrimaryKey(id);
		if (project == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		// 根据项目id查询活动开关打开的活动信息
		CampaignModelExample campaignEx = new CampaignModelExample();
		campaignEx.createCriteria().andProjectIdEqualTo(id).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignEx);
		
		String status = map.get("status").toString();
		if (StatusConstant.PROJECT_PAUSE.equals(status)) {
			
			// 根据监测码的使用情况变更监测码历史记录表信息
			if (campaigns != null && !campaigns.isEmpty()) {
				for (CampaignModel campaign : campaigns) {
					// 项目下可对应多个活动，变更每个活动开关打开的监测码记录记录信息
					if (campaign.getStartDate().after(new Date())) {
						// 如果是未投活动即活动的开始时间在今天之后，则删除监测码历史记录表中该活动监测码未开始使用的信息
						landpageService.deleteCodeHistoryInfo(campaign.getId());
					} else {
						// 如果是在投活动活动的开始时间在今天之前，更新监测码使用结束时间
						landpageService.updateCodeHistoryInfo(campaign.getId());
					}
				}
			}
			
			//暂停
			pauseProject(project);
		} else if (StatusConstant.PROJECT_PROCEED.equals(status)) {
			
			// 根据监测码的使用情况变更监测码历史记录表信息
			if (campaigns != null && !campaigns.isEmpty()) {
				for (CampaignModel campaign : campaigns) {
					// 项目下可对应多个活动，变更每个活动开关打开的监测码记录记录信息
					landpageService.creativeCodeHistoryInfo(campaign.getId(), campaign.getLandpageId());
				}
			}
			
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
		File excelFile= new File(excelSavePath + "/" + id + ".xlsx");
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
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
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
			excelFile = new File(excelSavePath + "/" + id + ".xlsx");
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
    public List<ProjectBean> listProjects(String name, Long startDate, Long endDate, String advertiserId, String sortKey, String sortType) throws Exception {
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

		//设置排序
		if (sortKey == null || sortKey.isEmpty()) {
			// 设置按更新时间降序排序
			example.setOrderByClause("create_time DESC");
		} else {
			if (sortType != null && sortType.equals(StatusConstant.SORT_TYPE_DESC)) {
				example.setOrderByClause(sortKey + " DESC");
			} else if (sortType != null && sortType.equals(StatusConstant.SORT_TYPE_ASC)) {
				example.setOrderByClause(sortKey + " ASC");
			} else {
				throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
			}
		}
		List<ProjectModel> projects = projectDao.selectByExample(example);
		List<ProjectBean> beans = new ArrayList<ProjectBean>();

		for (ProjectModel model : projects) {
			ProjectBean bean = modelMapper.map(model, ProjectBean.class);
			//查询属性，并放如结果中
			getParam4Bean(bean);

			if (startDate != null && endDate != null) {
				//查询每个项目的投放信息
//				getData(beginTime, endTime, bean);
				ProjectBean data = (ProjectBean)dataService.getProjectData(model.getId(), startDate, endDate);
				BeanUtils.copyProperties(data, bean, "id", "name", "advertiserId", "advertiserName", "industryId", 
						"industryName", "totalBudget", "remark", "status", "effectFields", "statics", "rules");
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
//    private void getData(Long beginTime, Long endTime,ProjectBean bean) throws Exception {
//    	//查询活动
//    	CampaignModelExample campaignExample = new CampaignModelExample();
//    	campaignExample.createCriteria().andProjectIdEqualTo(bean.getId());
//    	List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
//    	BasicDataBean dataBean = new BasicDataBean();//在此处创建bean，并初始化各个参数，保证所有数据都能返回，即便都是零
//    	dataService.formatBeanParams(dataBean);
//    	dataService.formatBeanRate(dataBean);
//    	if (campaigns != null && !campaigns.isEmpty()) {
//    		List<String> campaignIds = new ArrayList<String>();
//    		for (CampaignModel campaign : campaigns) {
//    			campaignIds.add(campaign.getId());
//    		}
//    		CreativeModelExample cExample = new CreativeModelExample();
//    		cExample.createCriteria().andCampaignIdIn(campaignIds);
//    		List<CreativeModel> list = creativeDao.selectByExample(cExample);
//    		List<String> creativeIds = new ArrayList<String>();
//    		if (list != null && !list.isEmpty()) {
//    			for (CreativeModel model : list) {
//    				creativeIds.add(model.getId());
//    			}
//    		}
//    		dataBean = creativeService.getCreativeDatas(creativeIds, beginTime, endTime);
//    	}
//    	bean.setImpressionAmount(dataBean.getImpressionAmount());
//    	bean.setClickAmount(dataBean.getClickAmount());
//    	bean.setTotalCost(dataBean.getTotalCost());
//    	bean.setJumpAmount(dataBean.getJumpAmount());
//    	bean.setImpressionCost(dataBean.getImpressionCost());
//    	bean.setClickCost(dataBean.getClickCost());
//    	bean.setClickRate(dataBean.getClickRate());
//    	bean.setJumpCost(dataBean.getJumpCost());
//		//修正成本
//		bean.setAdxCost(dataBean.getAdxCost());
//
//	}

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

    	//查询静态值
		List<StaticvalModel> staticModels = listStaticsByProjectId(projectId);
		if (staticModels != null) {
			int len = staticModels.size();
			StaticvalBean[] staticvalBeans = new StaticvalBean[len];
			for(int i=0; i<len; i++){
				StaticvalModel staticvalModel = staticModels.get(i);
				StaticvalBean staticvalBean  = new StaticvalBean();
				staticvalBean.setId(staticvalModel.getId());
				staticvalBean.setName(staticvalModel.getName());
				staticvalBean.setValue(staticvalModel.getValue());;
				staticvalBean.setUpdateDate(staticvalModel.getUpdateTime());
				staticvalBeans[i] = staticvalBean;
			}
			bean.setStaticvals(staticvalBeans);;
		}

		// 查询规则
		List<RuleModel> rulesModel = listRulesByProjectId(projectId);
		if (rulesModel != null) {
			int len = rulesModel.size();
			RuleFormulasBean[] rules = new RuleFormulasBean[len];
			for (int i=0; i<len; i++) {
				RuleModel rule = rulesModel.get(i);
				// 根据静态id查询静态值信息
				StaticvalModel staticvalModel = staticvalDao.selectByPrimaryKey(rule.getStaticvalId());
				Staticval staticval = null;
				if (staticvalModel != null) {					
					staticval = new Staticval();
					staticval.setId(staticvalModel.getId());
					staticval.setName(staticvalModel.getName());
					staticval.setValue(staticvalModel.getValue());
				}
				// 将查询到的信息放到bean中
				RuleFormulasBean ruleBean = new RuleFormulasBean();
				ruleBean.setId(rule.getId());
				ruleBean.setName(rule.getName());
				ruleBean.setTriggerCondition(rule.getTriggerCondition());
				ruleBean.setRelation(rule.getRelation());
				ruleBean.setStaticval(staticval);
				rules[i] = ruleBean;
			}
			bean.setRules(rules);
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

	/**
	 * 转化字段命名
	 * @param fieldId
	 * @param map
	 * @throws Exception 
	 */
	@Transactional
    public void changeEffectName(String fieldId, Map<String, String> map) throws Exception
    {
		String name = map.get("name");
		// 判断id和名称参数是否齐全
        if (StringUtils.isEmpty(fieldId) || StringUtils.isEmpty(name))
        {
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }
        // 判断转换值名称的长度
        if (name.length() > 100) {
        	throw new IllegalArgumentException(PhrasesConstant.LENGTH_ERROR_NAME);
        }
        
        // 判断同一项目下转换值名称是否相同
        EffectDicModel effectDic = effectDicDao.selectByPrimaryKey(fieldId);
        String projectId = effectDic.getProjectId();
        checkSameOfEffectDicName(name,projectId);
        
        // 判断同一项目下转换值与静态值是否相同
        checkSameOfStaticName(name,projectId);
        
        // 判断转化值名称是否使用：展现数、点击数、二跳、成本、修正成本
        checkUseFixedName(name);
        
        // 更新数据库
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
		//判断转换值对应的名称是否为空
		EffectDicModel effectDicModel = effectDicDao.selectByPrimaryKey(fieldId);
		if(effectDicModel == null || effectDicModel.getColumnName()==null  || effectDicModel.getColumnName().isEmpty()){
			throw new IllegalArgumentException(PhrasesConstant.EFFECTDIC_NAME_IS_NULL);
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
        // TODO : zytosee
        double redisBudget = Double.parseDouble(jedis.get(projectBudgetKey));

        // 已消耗掉的项目预算(分)
        double usedBudget = oldBudget * 100 - redisBudget;

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
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
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
	
	/**
	 * 创建规则
	 * @param bean 规则+公式
	 * @throws Exception
	 */
	@Transactional
	public void createRule(RuleFormulasBean bean) throws Exception {
		String ruleName = bean.getName();		

		// 验证同一项目下规则名称是否存在
		checkSameOfRuleName(ruleName,bean.getProjectId());
		
		// 判断触发条件、关系、静态值是否为空：为空则三者同时为空，其中一个不为空则都不为空
		checkRuleInfo(bean.getRelation(),bean.getRelation(),bean.getStaticvalId());
		
		// 判断公式是否合法
		String triggerCondition = bean.getTriggerCondition();
		if (triggerCondition != null && !triggerCondition.isEmpty()) {
			if (!isFormula(triggerCondition)) {
				throw new IllegalArgumentException(formulaErrorInfo(ruleName));
			}
		}		

		// 判断项目是否存在
		checkHaveProject(bean.getProjectId());

		// 插入规则
		String ruleId = UUIDGenerator.getUUID();
		bean.setId(ruleId);
		RuleModel rule = modelMapper.map(bean, RuleModel.class);
		ruleDao.insertSelective(rule);				
		
		// 添加公式
		addFormula(bean,ruleId);	
		
	}
	
	/**
	 * 添加公式
	 * @param ruleID 规则id
	 * @throws Exception
	 */
	private void addFormula(RuleFormulasBean bean,String ruleId) throws Exception {
		// 公式
		Formulas[] formulas = bean.getFormulas();
		
		// FIXME : 没有公式抛异常，判断为空即可
		if (formulas == null || formulas.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.FORMULA_IS_NULL);
		}
		
		// 判断权重之和是否为1
		float weight = 0;
		for (Formulas formulaBean : formulas) {
			weight += formulaBean.getWeight();
		}
		if (weight != 1) {
			throw new IllegalArgumentException(PhrasesConstant.WEIGHTS_ISNOT_CORRECT);
		}
		
		// 添加公式		
		for (Formulas formulaBean : formulas) {
			// 验证同一项目下公式名称是否存在
			// 1.根据传来的公式名称查询公式信息
			FormulaModelExample FormulaEx = new FormulaModelExample();
			FormulaEx.createCriteria().andNameEqualTo(formulaBean.getName());
			List<FormulaModel> formulaList = formulaDao.selectByExample(FormulaEx);
			if (formulaList != null && !formulaList.isEmpty()) {
				// 2.如果存在相同名称，判断是否在同一项目下
				for (FormulaModel formula : formulaList) {
					// 根据规则id查询规则信息
					RuleModel ruleModel = ruleDao.selectByPrimaryKey(formula.getRuleId());
					String projectId = ruleModel.getProjectId();
					// 公式名称相同的条件下查到的项目id如果与新建公式对应的项目id相同，则说明在同一项目下重名
					if (projectId.equals(bean.getProjectId())) {
						throw new DuplicateEntityException(PhrasesConstant.FORMULA_NAME_NOT_REPEAT);
					}					
				}				
			}
			
			// 判断正向游标和负向游标的正负号是否相同
			Double forwardVernier = formulaBean.getForwardVernier();
			Double negativeVernier = formulaBean.getNegativeVernier();
			if (!(forwardVernier > 0 && negativeVernier < 0 || forwardVernier < 0 && negativeVernier > 0)) {
				throw new IllegalArgumentException(PhrasesConstant.VERNIER_SIGN_SYMBOL_SAME);
			}
			
			// 判断公式是否合法
			if (!isFormula(formulaBean.getFormula())) {
				throw new IllegalArgumentException(formulaErrorInfo(formulaBean.getName()));
			}						
			
			// 判断静态值是否为空
			String staticvalId = formulaBean.getStaticvalId();
			checkHaveStatics(staticvalId);

			FormulaModel formulaModel = modelMapper.map(formulaBean, FormulaModel.class);
			formulaModel.setId(UUIDGenerator.getUUID());
			formulaModel.setRuleId(ruleId);
			formulaDao.insertSelective(formulaModel);
		}				
	}
	
	/**
	 * 根据ID查询规则
	 * @param id 规则ID
	 * @return 
	 * @throws Exception
	 */
	@Transactional
	public RuleFormulasBean getRule(String id) throws Exception {
		// 查询规则表中查询规则相关信息
		RuleModel rule = ruleDao.selectByPrimaryKey(id);
		if (rule == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		RuleFormulasBean ruleFormulasBean = modelMapper.map(rule, RuleFormulasBean.class);
		// 查询静态值信息
		StaticvalModel staticModel = staticvalDao.selectByPrimaryKey(rule.getStaticvalId());
		Staticval staticval = null;
		if (staticModel != null) {
			staticval = new Staticval();
			staticval.setId(staticModel.getId());
			staticval.setName(staticModel.getName());
			staticval.setValue(staticModel.getValue());
		}
		ruleFormulasBean.setStaticval(staticval);
		// 从公式表中查询规则对应的公式信息，并放到结果中
		listFormulas(ruleFormulasBean);
		return ruleFormulasBean ;		
	}
	
	/**
	 * 查询规则对应的公式
	 * @param ruleFormulasBean
	 * @throws Exception
	 */
	private void listFormulas(RuleFormulasBean ruleFormulasBean) throws Exception {
		// 根据规则id查询公式
		String ruleId = ruleFormulasBean.getId();
		FormulaModelExample formulasEx = new FormulaModelExample();
		formulasEx.createCriteria().andRuleIdEqualTo(ruleId);
		// 按照权重从高到低降序
		formulasEx.setOrderByClause("weight DESC");
		List<FormulaModel> formulasModel = formulaDao.selectByExample(formulasEx);
		// 返回公式数组
		if (formulasModel != null && !formulasModel.isEmpty()) {
			int size = formulasModel.size();
			Formulas[] formulas = new Formulas[size];
			for (int i=0; i<size; i++) {
				Formulas formula = new Formulas();
				// 每条公式信息
				FormulaModel formulaModel = formulasModel.get(i);
				// 根据静态值id获取静态值相关信息
				String staticId = formulaModel.getStaticvalId();
				StaticvalModel staticModel = staticvalDao.selectByPrimaryKey(staticId);
				if (staticModel != null) {
					Staticvals staticval = new Staticvals();
					staticval.setId(staticModel.getId());
					staticval.setName(staticModel.getName());
					staticval.setValue(staticModel.getValue());
					formula.setStaticval(staticval);
				}				
				// 将每条公式信息放到数组中				
				formula.setId(formulaModel.getId());
				formula.setName(formulaModel.getName());
				formula.setFormula(formulaModel.getFormula());
				formula.setForwardVernier(formulaModel.getForwardVernier());
				formula.setNegativeVernier(formulaModel.getNegativeVernier());				
				formula.setRuleId(ruleId);			
				formula.setWeight(formulaModel.getWeight());				
				formulas[i] = formula;
			}
			// 将结果放到bean中
			ruleFormulasBean.setFormulas(formulas);			
		}
	}
	
	/**
	 * 批量删除规则
	 * @param ids 要删除的规则id
	 * @throws Exception
	 */
	@Transactional
	public void deleteRules(String[] ids) throws Exception {
		if (ids.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		// 判断规则是否存在
		RuleModelExample rulesEx = new RuleModelExample();
		rulesEx.createCriteria().andIdIn(Arrays.asList(ids));
		List<RuleModel> rules = ruleDao.selectByExample(rulesEx);
		if (rules == null || rules.isEmpty() || rules.size() < ids.length) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		//删除规则下的公式，一条规则可对应多个公式
		for (RuleModel rule : rules) {
			// 删除每个规则下的公式
			deleteFormula(rule.getId());
		}
		
		// 删除规则
		ruleDao.deleteByExample(rulesEx);
	}
	
	/**
	 * 编辑规则
	 * @param id 规则id
	 * @param bean 规则及其对应的公式信息
	 * @throws Exception
	 */
	@Transactional
	public void updateRule(String id, RuleFormulasBean bean) throws Exception {
		// 判断规则是否存在
		RuleModel rule = ruleDao.selectByPrimaryKey(id);
		if (rule == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		} else {
			// 判断同一项目下规则名称是否重复
			String nameInDB = rule.getName();  // 数据库中的规则名称
			String name = bean.getName();      // 欲修改规则名称
			if (!nameInDB.equals(name)) {			
				// 如果数据库中其它规则的名称与欲修改成的规则名称重复，则禁止修改
				checkSameOfRuleName(name,bean.getProjectId());
			}			
		}	
		
		// 判断触发条件、关系、静态值是否为空：为空则三者同时为空，其中一个不为空则都不为空
		checkRuleInfo(bean.getRelation(),bean.getRelation(),bean.getStaticvalId());
		
		// FIXME ： 判断条件是否为空
		// 判断公式是否合法
		String triggerCondition = bean.getTriggerCondition();
		if (triggerCondition != null && !triggerCondition.isEmpty()) {
			if (!isFormula(triggerCondition)) {
				throw new IllegalArgumentException(formulaErrorInfo(bean.getName()));
			}
		}
		
		// 判断项目是否存在
		checkHaveProject(bean.getProjectId());
		
		bean.setId(id);
		RuleModel ruleModel = modelMapper.map(bean, RuleModel.class);
		
		// 删除公式
		deleteFormula(id);
		// 添加公式
		addFormula(bean,id);
		
		// 更新规则
		ruleDao.updateByPrimaryKey(ruleModel);
	}
	
	/**
	 * 判断项目是否存在
	 * @param projectId 项目id
	 * @throws Exception
	 */
	private void checkHaveProject(String projectId) throws Exception {
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		if (project == null) {
			throw new IllegalArgumentException(PhrasesConstant.PROJECT_INFO_IS_NULL);
		}
	}
	
	/**
	 * 判断静态值是否存在
	 * @param staticId 静态值id
	 * @throws Exception
	 */
	private void checkHaveStatics(String staticId) throws Exception {
		StaticvalModel statics = staticvalDao.selectByPrimaryKey(staticId);
		if (statics == null) {
			throw new IllegalArgumentException(PhrasesConstant.STATICS_INFO_IS_NULL);				
		}
	}
	
	/**
	 * 根据规则id删除公式
	 * @param ruleId 规则id
	 * @throws Exception
	 */
	public void deleteFormula(String ruleId) throws Exception {
		// 查询规则下的公式
		FormulaModelExample formulaEx = new FormulaModelExample();
		formulaEx.createCriteria().andRuleIdEqualTo(ruleId);
		
		// 删除公式
		formulaDao.deleteByExample(formulaEx);
	}
	
	/**
	 * 根据项目id查询规则
	 * @param projectId 项目id
	 * @return
	 * @throws Exception
	 */
	private List<RuleModel> listRulesByProjectId(String projectId) throws Exception {
		RuleModelExample ruleEx = new RuleModelExample();
		ruleEx.createCriteria().andProjectIdEqualTo(projectId);
		
		List<RuleModel> rules = ruleDao.selectByExample(ruleEx);
		return rules;
	}
	
	/**
	 * 验证公式是否合法
	 * @param formula 公式/规则
	 * @return
	 * @throws Exception
	 */
	private boolean isFormula(String formula) throws Exception {
		// 替换公式中的变量
		Pattern pattern = Pattern.compile("[{]+[a-zA-Z0-9-]+[}]");
		Matcher matcher = pattern.matcher(formula);
		while (matcher.find()) {
			formula = formula.replace(matcher.group(), "1");
		}
		for (int i = 1; i <= 10; i++) {
			formula = formula.replace("A" + i, "1");
		}
		for (int i = 1; i <= 5; i++) {
			formula = formula.replace("B" + i, "1");
		}
		
		if (!(formula.indexOf("11") < 0)) {
			return false;
		}
	
		// 调用公式验证方法
		if (FormulaUtils.checkExpression(formula)) {
			return true;
		} 
		return false;
	}
	
	/**
	 * 解析规则/公式的错误信息
	 * @param name 名称
	 * @return
	 * @throws Exception
	 */
	private String formulaErrorInfo(String name) throws Exception {
		if (name != null && !name.isEmpty()) {
			return name + ":" + PhrasesConstant.FORMULA_ISNOT_LEGAL;
		} else {
			return PhrasesConstant.FORMULA_ISNOT_LEGAL;
		}
	}
	
	/**
	 * 验证同一项目下规则名称是否相同
	 * @param name 规则名称
	 * @param projectId 项目id
	 * @throws Exception
	 */
	private void checkSameOfRuleName(String name,String projectId) throws Exception {	
		// 根据规则名称和项目id查询规则信息
		RuleModelExample ruleEx = new RuleModelExample();
		ruleEx.createCriteria().andNameEqualTo(name).andProjectIdEqualTo(projectId);
		List<RuleModel> ruleList = ruleDao.selectByExample(ruleEx);
		if (ruleList != null && !ruleList.isEmpty()) {
			// 如果项目下有相同的名称，则抛异常
			 throw new DuplicateEntityException(PhrasesConstant.RULE_NAME_NOT_REPEAT);
		}
	}
	
	/**
	 * 判断触发条件、关系、静态值是否为空：为空则三者同时为空，其中一个不为空则都不为空
	 * @param condition 触发条件
	 * @param relation 关系
	 * @param staticId 静态值id
	 * @throws Exception
	 */
	private void checkRuleInfo(String condition,String relation,String staticId) throws Exception {
		if ((condition != null && !condition.isEmpty()) || (relation != null && !relation.isEmpty())
				|| (staticId != null && !staticId.isEmpty())) {
			if (condition == null || condition.isEmpty()) {
				throw new IllegalArgumentException(PhrasesConstant.CONDITION_IS_NULL);
			}
			if (relation == null || relation.isEmpty()) {
				throw new IllegalArgumentException(PhrasesConstant.RELATION_IS_NULL);
			}
			if (staticId == null || staticId.isEmpty()) {
				throw new IllegalArgumentException(PhrasesConstant.RULE_REFERENCE_VALUE_NULL);
			}
		}
	}
	
	/**
	 * 创建静态值
	 * @param bean
	 * @throws Exception 
     */
	@Transactional
	public void createStatic(StaticvalBean bean) throws Exception{
		String name = bean.getName();
		String projectId = bean.getProjectId();
		
		// 验证同一项目下静态值名称是否相同
		checkSameOfStaticName(name,projectId);
		
		// 判断同一项目下静态值与转换值名称是否相同
		checkSameOfEffectDicName(name,projectId);
		
		// 判断静态值名称是否使用：展现数、点击数、二跳、成本、修正成本
		checkUseFixedName(name);
				
		// 插入MySQL
		StaticvalModel staticModel = modelMapper.map(bean, StaticvalModel.class);
		String staticId=UUIDGenerator.getUUID();
		staticModel.setId(staticId);
		bean.setId(staticId);
		staticvalDao.insertSelective(staticModel);
	}

	/**
	 *根据项目id查找静态值
	 * @return
     */
	private List<StaticvalModel> listStaticsByProjectId(String projectId){
		StaticvalModelExample staticExample = new StaticvalModelExample();
		staticExample.createCriteria().andProjectIdEqualTo(projectId);
		staticExample.setOrderByClause(" update_time desc ");//按更新时间逆序排序
		List<StaticvalModel> staticInDB = staticvalDao.selectByExample(staticExample);
		return staticInDB;
	}

	/**
	 * 更新静态值
	 * @param id
	 * @param bean
     */
	@Transactional
	public void updateStaticValue(String id, StaticvalBean bean){
		// 判断指定ID的项目在MySQL中是否存在
		StaticvalModel staticInDB = staticvalDao.selectByPrimaryKey(id);
		if (staticInDB == null)
		{
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		else
		{
			double valueInDB = staticInDB.getValue();
			double value = bean.getValue();
			if (valueInDB!=value)
			{
				staticInDB.setValue(value);
				staticInDB.setUpdateTime(new Date());
				staticvalDao.updateByPrimaryKeySelective(staticInDB);
			}
		}
	}

	/**
	 * 更新静态值名称
	 * @param id
	 * @param bean
	 * @throws Exception 
     */
	@Transactional
	public void updateStaticName(String id,StaticvalBean bean) throws Exception{
		// 判断指定ID的项目在MySQL中是否存在
		StaticvalModel staticInDB = staticvalDao.selectByPrimaryKey(id);
		if (staticInDB == null)
		{
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		else
		{
			String nameInDB = staticInDB.getName(); // 数据库中的静态值名称
			String name = bean.getName();           // 欲修改的静态值名称
			
			if (!nameInDB.equals(name))
			{
				// 项目id			
				String projectId = staticInDB.getProjectId();
				
				// 判断同一项目下的静态值名称是否相同
				checkSameOfStaticName(name,projectId);
				
				// 判断同一项目下静态值与转换值名称是否相同
				checkSameOfEffectDicName(name,projectId);
				
				// 判断静态值名称是否使用：展现数、点击数、二跳、成本、修正成本
				checkUseFixedName(name);
				
				// 更新数据库
				staticInDB.setName(name);
				staticInDB.setUpdateTime(new Date());
				staticvalDao.updateByPrimaryKeySelective(staticInDB);
			}
		}
	}

	/**
	 * 删除静态值
	 * @param ids
	 * @throws Exception
     */
	@Transactional
	public void  deleteStatics(String[] ids) throws Exception{
		if(ids.length ==0){
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}

		// 判断指定的多个ID是否在MySQL中存在
		StaticvalModelExample staticExample = new StaticvalModelExample();
		staticExample.createCriteria().andIdIn(Arrays.asList(ids));

		List<StaticvalModel> staticInDB = staticvalDao.selectByExample(staticExample);
		if (staticInDB == null || staticInDB.size() < ids.length)
		{
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}

		// 如果有一个静态值下被公式或规则引用，则放弃整个删除
		RuleModelExample ruleModelExample = new RuleModelExample();
		ruleModelExample.createCriteria().andStaticvalIdIn(Arrays.asList(ids));
		List<RuleModel> ruleModels = ruleDao.selectByExample(ruleModelExample);
		if(ruleModels != null && ruleModels.size()>0){
			throw new ResourceNotFoundException(PhrasesConstant.STATIC_RULER_DELETE_ERROR);
		}

		FormulaModelExample formulaModelExample = new FormulaModelExample();
		formulaModelExample.createCriteria().andStaticvalIdIn(Arrays.asList(ids));
		List<FormulaModel> formulaModels = formulaDao.selectByExample(formulaModelExample);
		if(formulaModels != null && formulaModels.size()>0){
			throw new ResourceNotFoundException(PhrasesConstant.STATIC_FORMULATE_DELETE_ERROR);
		}

		// 从MySQL中批量删除指定ID的静态值
		staticvalDao.deleteByExample(staticExample);
	}
	
	/**
	 * 判断同一项目下静态值名称是否重复
	 * @param name 静态值名称
	 * @param projectId 项目id
	 * @throws Exception
	 */
	private void checkSameOfStaticName(String name,String projectId) throws Exception {
		// 根据静态值名称和项目id查询转换值信息
		StaticvalModelExample staticEx = new StaticvalModelExample();
		staticEx.createCriteria().andNameEqualTo(name).andProjectIdEqualTo(projectId);
		List<StaticvalModel> statics = staticvalDao.selectByExample(staticEx);
		if (statics != null && !statics.isEmpty()) {
			// 如果项目id下存在静态值名称，则抛出异常
			throw new DuplicateEntityException(PhrasesConstant.STATICS_NAME_IS_SAME);
		}
		
	}
	
	/**
	 * 判断同一项目转换值名称是否重复
	 * @param name 转化值名称
	 * @param projectId
	 * @throws Exception
	 */
	private void checkSameOfEffectDicName(String name, String projectId) throws Exception {
		// 根据转化值名称和项目id查询转换值信息
		EffectDicModelExample effectDicEx = new EffectDicModelExample();
		effectDicEx.createCriteria().andColumnNameEqualTo(name).andProjectIdEqualTo(projectId);
		List<EffectDicModel> effectDic = effectDicDao.selectByExample(effectDicEx);
		if (effectDic != null && !effectDic.isEmpty()) {
			// 如果项目id下存在转化值名称，则抛出异常
			throw new DuplicateEntityException(PhrasesConstant.EFFECTDIC_NAME_IS_SAME);
		}
	}
	
	/**
	 * 判断静态值/转化值名称是否使用：展现数、点击数、二跳、成本、修正成本
	 * @param name 静态值/转化值名称	
	 * @throws Exception
	 */
	private void checkUseFixedName(String name) throws Exception {
		if(CodeTableConstant.DISPLAY_AMOUNT.equals(name) || CodeTableConstant.CLICK_AMOUNT.equals(name) 
				|| CodeTableConstant.JUMP_AMOUNT.equals(name) || CodeTableConstant.COST.equals(name)
				|| CodeTableConstant.ADX_COST.equals(name)) {
			throw new DuplicateEntityException(PhrasesConstant.USED_FIXED_NAME);
		}
	}
}
