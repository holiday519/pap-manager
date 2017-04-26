package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.KpiModel;
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
import com.pxene.pap.repository.basic.IndustryDao;
import com.pxene.pap.repository.basic.KpiDao;
import com.pxene.pap.repository.basic.ProjectDao;

import org.slf4j.Logger;
/*import org.apache.log4j.Logger;*/
import org.slf4j.LoggerFactory; 

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
	private KpiDao kpiDao;
	
	@Autowired
	private LaunchService launchService;
	
	//Logger log = Logger.getLogger("ProjectService"); 
	
	/**
	 * 创建项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createProject(ProjectBean bean) throws Exception {
		// 验证名称重复
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andNameEqualTo(bean.getName());
		List<ProjectModel> projects = projectDao.selectByExample(example);
		if (projects != null && !projects.isEmpty()) {
			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		ProjectModel project = modelMapper.map(bean, ProjectModel.class);
		/*project.setId(UUID.randomUUID().toString());*/
		project.setId(UUIDGenerator.getUUID());
		project.setStatus(StatusConstant.PROJECT_PROCEED);
		projectDao.insertSelective(project);
		
		BeanUtils.copyProperties(project, bean);
	}
	
	/**
	 * 编辑项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateProject(String id, ProjectBean bean) throws Exception {
		ProjectModel projectInDB = projectDao.selectByPrimaryKey(id);
		if (projectInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		} else {
			String nameInDB = projectInDB.getName();
			String name = bean.getName();
			if (!nameInDB.equals(name)) {
				ProjectModelExample projectExample = new ProjectModelExample();
				projectExample.createCriteria().andNameEqualTo(name);
				List<ProjectModel> projects = projectDao.selectByExample(projectExample);
				if (projects != null & !projects.isEmpty()) {
					throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
				}
			}
		}

		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andProjectIdEqualTo(id);
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
		if (campaigns != null && !campaigns.isEmpty()) {
			int budget = 0; 
			for (CampaignModel campaign : campaigns) {
				Integer totalBudget = campaign.getTotalBudget();
				if (totalBudget != null) {
					budget = budget + totalBudget;
				}
			}
			if (bean.getTotalBudget() < budget) {
				throw new IllegalArgumentException(PhrasesConstant.PROJECT_BUDGET_UNDER_CAMPAIGN_ALL);
			}
		}
		
		ProjectModel model = modelMapper.map(bean, ProjectModel.class);
		model.setId(id);

		projectDao.updateByPrimaryKeySelective(model);
	}
	
	/**
	 * 修改项目状态
	 * @param id
	 * @param map
	 */
	@Transactional
	public void updateProjectStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		ProjectModel project = projectDao.selectByPrimaryKey(id);
		if (project == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		String action = map.get("action").toString();
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			//暂停
			pauseProject(project);
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
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
	public void deleteProject(String id) throws Exception {
		
		ProjectModel projectInDB = projectDao.selectByPrimaryKey(id);
		if (projectInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		//查询出项目下活动
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(id);
		List<CampaignModel> list = campaignDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.PROJECT_HAVE_CAMPAIGN);
		}
		
		projectDao.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除项目
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteProjects(String[] ids) throws Exception {
		
		ProjectModelExample projectExample = new ProjectModelExample();
		projectExample.createCriteria().andIdIn(Arrays.asList(ids));
		
		List<ProjectModel> projectInDB = projectDao.selectByExample(projectExample);
		if (projectInDB == null || projectInDB.size() < ids.length) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		for (String id : ids) {
			//查询出项目下活动
			CampaignModelExample campaignExample = new CampaignModelExample();
			campaignExample.createCriteria().andProjectIdEqualTo(id);
			List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
			if (campaigns != null && !campaigns.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.PROJECT_HAVE_CAMPAIGN);
			}
		}
		
		projectDao.deleteByExample(projectExample);
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
    			Integer industryId = adv.getIndustryId();
    			IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
    			bean.setIndustryId(industryId);
				if (industryModel != null) {
					bean.setIndustryName(industryModel.getName());
    			}
    		}
    	}
    	String kpiId = bean.getKpiId();
    	KpiModel kpiModel = kpiDao.selectByPrimaryKey(kpiId);
    	if (kpiModel!=null) {
    		bean.setKpiName(kpiModel.getName());
    	}
    	
    }
    
	/**
	 * 按照项目投放（项目状态开启）
	 * @param projectIds
	 * @throws Exception
	 */
	@Transactional
	public void proceedProject(ProjectModel project) throws Exception {
		//查询改项目下活动状态为开启状态的活动信息
		String projectId = project.getId();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				String campaignId = campaign.getId();
				if (StatusConstant.CAMPAIGN_PROCEED.equals(campaign.getStatus()) 
						&& campaignService.isOnLaunchDate(campaignId)) {
					//如果活动状态为开启，并且在活动的投放时间里
					if (launchService.isFirstLaunch(campaignId)) {
						//如果campaignId不在redis中说明是第一次投放改活动，则将投放的基本信息写入到redis中
						launchService.write4FirstTime(campaign);
					}
					/*if (campaignService.isOnTargetTime(campaignId)) {*/
					if (campaignService.isOnTargetTime(campaignId)&& launchService.dailyBudgetJudge(campaignId) 
							&& launchService.dailyCounterJudge(campaignId)) {
						//如果在定向的时间里，将campaignId写入到redis的投放groups中
						//活动没有超出每天的日预算并且日均最大展现未达到上限
						//launchService.writeCampaignId(campaignId);
						boolean writeResult = launchService.launchCampaignRepeatable(campaignId);
						LOGGER.info("chaxunLaunchTrue." + campaignId);
						if(!writeResult){
							LOGGER.info("chaxunLaunchFalse." + campaignId);
							throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
						}
					}
				}				
			}
		}
		//项目投放之后修改状态
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
		//查询改项目下活动状态为开启状态的活动信息
		String projectId = project.getId();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		//从redis的groups中删除该项目下所有活动状态为开启的活动id
		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				//launchService.removeCampaignId(campaign.getId());
				//将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
				boolean removeResult = launchService.pauseCampaignRepeatable(campaign.getId());
				LOGGER.info("chaxunPauseTrue."+campaign.getId());
				if (!removeResult) {
					LOGGER.info("chaxunPauseFalse."+campaign.getId());
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}
		//项目暂停之后修改状态
		project.setStatus(StatusConstant.PROJECT_PAUSE);
		projectDao.updateByPrimaryKeySelective(project);
	}
	
}
