package com.pxene.pap.service;

import java.util.ArrayList;
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
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.beans.ProjectDetailBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.view.ProjectDetailModel;
import com.pxene.pap.domain.models.view.ProjectDetailModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.view.ProjectDetailDao;

@Service
public class ProjectService extends LaunchService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private ProjectDetailDao projectDetailDao;
	
	/**
	 * 创建项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createProject(ProjectBean bean) throws Exception{
		ProjectModel model = modelMapper.map(bean, ProjectModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			// 添加项目信息
			model.setStatus(StatusConstant.PROJECT_START);//状态
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
		CampaignModel model = campaignDao.selectByPrimaryKey(id);
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
			stopProject(id);
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
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
    public ProjectDetailBean selectProject(String id) throws Exception {
    	//从视图中查询项目所相关信息
        ProjectDetailModelExample example = new ProjectDetailModelExample();
        example.createCriteria().andIdEqualTo(id);
		List<ProjectDetailModel> models = projectDetailDao.selectByExample(example);
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		ProjectDetailModel model = models.get(0);
        ProjectDetailBean bean = modelMapper.map(model, ProjectDetailBean.class);
        
        return bean;
    }
    
    /**
     * 查询项目列表
     * @param name
     * @return
     * @throws Exception
     */
    public List<ProjectDetailBean> selectProjects(String name, String advertiserId) throws Exception {
    	
    	ProjectDetailModelExample example = new ProjectDetailModelExample();

		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		
		if (!StringUtils.isEmpty(advertiserId)) {
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		}
		
		List<ProjectDetailModel> projects = projectDetailDao.selectByExample(example);
		List<ProjectDetailBean> beans = new ArrayList<ProjectDetailBean>();
		
		if (projects == null || projects.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (ProjectDetailModel model : projects) {
			ProjectDetailBean bean = modelMapper.map(model, ProjectDetailBean.class);
			beans.add(bean);
		}
		
    	return beans;
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
				example.createCriteria().andProjectIdEqualTo(projectId);
				List<CampaignModel> campaigns = campaignDao.selectByExample(example);
				if (campaigns == null || campaigns.isEmpty()) {
					continue;
				}
				for (CampaignModel campaign : campaigns) {
					//活动是投放状态，并且活动可以投放
					if (StatusConstant.CAMPAIGN_START.equals(campaign.getStatus())
							&& campaignService.checkCampaignCanLaunch(campaign.getId())) {
						// 投放
						launch(campaign.getId());
					}
				}
				//项目投放之后修改状态
				projectModel.setStatus(StatusConstant.PROJECT_START);
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
				example.createCriteria().andProjectIdEqualTo(projectId);
				List<CampaignModel> campaigns = campaignDao.selectByExample(example);
				if (campaigns == null || campaigns.isEmpty()) {
					continue;
				}
				for (CampaignModel campaign : campaigns) {
					if (StatusConstant.CAMPAIGN_START.equals(campaign.getStatus())
							&& StatusConstant.PROJECT_START.equals(projectModel.getStatus())) {
						//移除redis中key
						pause(campaign.getId());
					}
				}
				//项目投放之后修改状态
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
				projectModel.setStatus(StatusConstant.CAMPAIGN_CLOSE);
				projectDao.updateByPrimaryKeySelective(projectModel);
			}
		}
	}
	
}
