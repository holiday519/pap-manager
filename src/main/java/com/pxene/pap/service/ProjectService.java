package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class ProjectService extends BaseService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CampaignService campaignService;
	
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
		try {
			model.setId(id);
			// 添加项目信息
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
			throw new NotFoundException();
		}

		ProjectModel projectModel = modelMapper.map(bean, ProjectModel.class);
		projectModel.setId(id);

		try {
			// 修改项目信息
			projectDao.updateByPrimaryKeySelective(projectModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
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
			throw new NotFoundException();
		}
		
		//查询出项目下活动
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(id);
		List<CampaignModel> list = campaignDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		projectDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
    public ProjectBean selectProject(String id) throws Exception {
        ProjectModel model = projectDao.selectByPrimaryKey(id);
        if (model == null) {
        	throw new NotFoundException();
        }
        
        ProjectBean bean = modelMapper.map(model, ProjectBean.class);
        
        return bean;
    }
    
    /**
     * 查询项目列表
     * @param name
     * @return
     * @throws Exception
     */
    public List<ProjectBean> selectProjects(String name) throws Exception {
    	
    	ProjectModelExample example = new ProjectModelExample();

		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		
		List<ProjectModel> projects = projectDao.selectByExample(example);
		List<ProjectBean> beans = new ArrayList<ProjectBean>();
		
		if (projects == null || projects.isEmpty()) {
			throw new NotFoundException();
		}
		
		for (ProjectModel mod : projects) {
			ProjectBean bean = modelMapper.map(mod, ProjectBean.class);
			beans.add(bean);
		}
		
    	return beans;
    }
    
	
}
