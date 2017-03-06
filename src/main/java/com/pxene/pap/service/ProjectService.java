package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.IndustryDao;
import com.pxene.pap.repository.basic.KpiDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class ProjectService extends LaunchService {
	
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
	
	/**
	 * 创建项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createProject(ProjectBean bean) throws Exception{
		ProjectModel model = modelMapper.map(bean, ProjectModel.class);
		
		if (bean != null && model != null && !StringUtils.isEmpty(bean.getName()))
		{
		    ProjectModelExample modelExample = new ProjectModelExample();
		    modelExample.createCriteria().andNameEqualTo(bean.getName());
		    List<ProjectModel> selectResult = projectDao.selectByExample(modelExample);
		    if (selectResult != null && !selectResult.isEmpty())
		    {
		        throw new IllegalArgumentException("项目名称已存在");
		    }
		}
		
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			// 添加项目信息
			model.setStatus(StatusConstant.PROJECT_PROCEED);//状态
			projectDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(model, bean);
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
			throw new ResourceNotFoundException();
		}

		ProjectModel model = modelMapper.map(bean, ProjectModel.class);
		model.setId(id);

		try {
			// 修改项目信息
			projectDao.updateByPrimaryKeySelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	/**
	 * 修改项目状态
	 * @param id
	 * @param map
	 */
	@Transactional
	public void updateProjectStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		ProjectModel model = projectDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		
		String action = map.get("action").toString();
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			//暂停
			pauseProject(id);
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
			//投放
			launchProject(id);
		} else if (StatusConstant.ACTION_TYPE_CLOSE.equals(action)) {
			//结束
//			stopProject(id);
		}else {
			throw new IllegalStatusException();
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
			throw new ResourceNotFoundException();
		}
		
		//查询出项目下活动
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(id);
		List<CampaignModel> list = campaignDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.PROJECT_HAS_CAMPAIGN);
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
		
		List<String> asList = Arrays.asList(ids);
		ProjectModelExample ex = new ProjectModelExample();
		ex.createCriteria().andIdIn(asList);
		
		List<ProjectModel> projectInDB = projectDao.selectByExample(ex);
		if (projectInDB == null || projectInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (String id : ids) {
			//查询出项目下活动
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andProjectIdEqualTo(id);
			List<CampaignModel> list = campaignDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.PROJECT_HAS_CAMPAIGN);
			}
		}
		
		projectDao.deleteByExample(ex);
	}

	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
    public ProjectBean selectProject(String id) throws Exception {
    	//从视图中查询项目所相关信息
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andIdEqualTo(id);
		ProjectModel model = projectDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		ProjectBean bean = modelMapper.map(model, ProjectBean.class);
        getParamForBean(bean);//查询属性，并放如结果中
        return bean;
    }
    
    /**
     * 查询项目列表
     * @param name
     * @return
     * @throws Exception
     */
    public List<ProjectBean> selectProjects(String name, Long beginTime, Long endTime, String advertiserId) throws Exception {
    	
    	ProjectModelExample example = new ProjectModelExample();
		if (!StringUtils.isEmpty(name) && StringUtils.isEmpty(advertiserId)) {
			example.createCriteria().andNameLike("%" + name + "%");
		} else if (!StringUtils.isEmpty(advertiserId) && StringUtils.isEmpty(name)) {
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		} else if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(advertiserId)) {
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andNameLike("%" + name + "%");
		}
		
		// 设置按更新时间降序排序
		example.setOrderByClause("update_time DESC");
		
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
	 * 按照项目投放
	 * @param projectIds
	 * @throws Exception
	 */
	@Transactional
	public void launchProject(String param) throws Exception {
		if (StringUtils.isEmpty(param)) {
			throw new ResourceNotFoundException();
		}
		String[] projectIds = param.split(",");
		
		for (String projectId : projectIds) {
			ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
			if (projectModel != null) {
				CampaignModelExample example = new CampaignModelExample();
				example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
				List<CampaignModel> campaigns = campaignDao.selectByExample(example);
				if (campaigns != null && !campaigns.isEmpty()) {
					for (CampaignModel campaign : campaigns) {
						// 投放
						launch(campaign.getId());
					}
				}
				//项目投放之后修改状态
				projectModel.setStatus(StatusConstant.PROJECT_PROCEED);
				projectDao.updateByPrimaryKeySelective(projectModel);
			}
		}
	}
	
	/**
	 * 按照项目暂停
	 * @param projectIds
	 * @throws Exception
	 */
	@Transactional
	public void pauseProject(String param) throws Exception {
		if (StringUtils.isEmpty(param)) {
			throw new ResourceNotFoundException();
		}
		String[] projectIds = param.split(",");
		
		for (String projectId : projectIds) {
			ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
			if (projectModel != null) {
				CampaignModelExample example = new CampaignModelExample();
				example.createCriteria().andProjectIdEqualTo(projectId).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
				List<CampaignModel> campaigns = campaignDao.selectByExample(example);
				if (campaigns != null && !campaigns.isEmpty()) {
					for (CampaignModel campaign : campaigns) {
						//移除redis中key
						pause(campaign.getId());
					}
				}
				//项目暂停之后修改状态
				projectModel.setStatus(StatusConstant.PROJECT_PAUSE);
				projectDao.updateByPrimaryKeySelective(projectModel);
			}
		}
	}
	
	/**
	 * 结束项目
	 * @param projectIds
	 * @throws Exception
	 */
	@Transactional
	public void stopProject(String param) throws Exception {
		if (StringUtils.isEmpty(param)) {
			throw new ResourceNotFoundException();
		}
		String[] projectIds = param.split(",");
		
		for (String projectId : projectIds) {
			ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
			if (projectModel != null) {
				CampaignModelExample example = new CampaignModelExample();
				example.createCriteria().andProjectIdEqualTo(projectId);
				List<CampaignModel> campaigns = campaignDao.selectByExample(example);
				if (campaigns == null || campaigns.isEmpty()) {
					continue;
				}
				for (CampaignModel campaign : campaigns) {
					deleteKeyFromRedis(campaign.getId());
				}
				//项目投放之后修改状态
//				projectModel.setStatus(StatusConstant.CAMPAIGN_CLOSE);
				projectDao.updateByPrimaryKeySelective(projectModel);
			}
		}
	}
	
}
