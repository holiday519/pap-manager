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
import com.pxene.pap.domain.model.basic.ProjectKpiModel;
import com.pxene.pap.domain.model.basic.ProjectKpiModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectKpiModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class ProjectService extends BaseService {
	
	@Autowired
	private ProjectModelMapper projectMapper;
	
	@Autowired
	private ProjectKpiModelMapper projectKpiMapper;
	
	@Autowired
	private CampaignModelMapper campaignMapper;
	
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
		try {
			String id = UUID.randomUUID().toString();
			model.setId(id);
			// project-kpi 添加关联表数据
			String kpiId = bean.getKpiId();
			ProjectKpiModel kpiModel = new ProjectKpiModel();
			kpiModel.setId(UUID.randomUUID().toString());
			Integer value = bean.getValue();
			kpiModel.setKpiId(kpiId);
			kpiModel.setProjectId(id);
			kpiModel.setValue(value);
			projectKpiMapper.insertSelective(kpiModel);
			// 添加项目信息
			projectMapper.insertSelective(model);
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
	public void updateProject(String id, ProjectBean bean) throws Exception{
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}

		ProjectModel projectInDB = projectMapper.selectByPrimaryKey(id);
		if (projectInDB == null) {
			throw new NotFoundException();
		}

		ProjectModel projectModel = modelMapper.map(bean, ProjectModel.class);
		projectModel.setId(id);

		// 修改关联关系表信息
		String kpiId = bean.getKpiId();
		Integer value = bean.getValue();
		ProjectKpiModel pkModel = new ProjectKpiModel();
		pkModel.setValue(value);
		pkModel.setKpiId(kpiId);
		ProjectKpiModelExample pkExample = new ProjectKpiModelExample();
		pkExample.createCriteria().andProjectIdEqualTo(id);
		projectKpiMapper.updateByExampleSelective(pkModel, pkExample);
		try {
			// 修改项目信息
			projectMapper.updateByPrimaryKeySelective(projectModel);
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
		ProjectModel projectInDB = projectMapper.selectByPrimaryKey(id);
		if (projectInDB == null) {
			throw new NotFoundException();
		}
		
		//查询出项目下活动
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(id);
		List<CampaignModel> list = campaignMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			// TODO 不级联删除
			for (CampaignModel model : list) {
				String campaignId = model.getId();
				// 执行活动删除方法
				campaignService.deleteCampaign(campaignId);
			}
		}
		
		//删除项目——KPI指标 关联关系
		ProjectKpiModelExample pkExample = new ProjectKpiModelExample();
		pkExample.createCriteria().andProjectIdEqualTo(id);
		projectKpiMapper.deleteByExample(pkExample);
		projectMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
    public ProjectBean selectProject(String id) throws Exception {
        ProjectModel model = projectMapper.selectByPrimaryKey(id);
        if (model == null) {
        	throw new NotFoundException();
        }
        
        ProjectBean bean = addKpiIdAndValueToBean(modelMapper.map(model, ProjectBean.class), id);
        
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
		
		List<ProjectModel> projects = projectMapper.selectByExample(example);
		List<ProjectBean> beans = new ArrayList<ProjectBean>();
		
		if (projects == null || projects.isEmpty()) {
			throw new NotFoundException();
		}
		
		for (ProjectModel mod : projects) {
			ProjectBean bean = modelMapper.map(mod, ProjectBean.class);
			beans.add(addKpiIdAndValueToBean(bean, mod.getId()));
		}
		
    	return beans;
    }
    
    /**
     * 查询kpiId以及Kpi值放入Bean中
     * @param bean
     * @param projectId
     * @return
     */
    private ProjectBean addKpiIdAndValueToBean(ProjectBean bean, String projectId) {
    	
    	ProjectKpiModelExample example = new ProjectKpiModelExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<ProjectKpiModel> list = projectKpiMapper.selectByExample(example);
        //一对一；只取第1条
        if (list != null && !list.isEmpty()) {
        	bean.setKpiId(list.get(0).getKpiId());
        	bean.setValue(list.get(0).getValue());
        }
        
    	return bean;
    }
	
}
