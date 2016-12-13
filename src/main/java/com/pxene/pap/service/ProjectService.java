package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import com.pxene.pap.domain.model.basic.ProjectKpiModel;
import com.pxene.pap.domain.model.basic.ProjectKpiModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectKpiModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class ProjectService {
	
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
	public String createProject(ProjectBean bean) throws Exception{
		int num;
		String name = bean.getName();
		ProjectModelExample ex = new ProjectModelExample();
		ex.createCriteria().andNameEqualTo(name);
		List<ProjectModel> list = projectMapper.selectByExample(ex);
		if (!list.isEmpty()) {
			return "项目名称重复";
		}
		String id = UUID.randomUUID().toString();
		String advertiserId = bean.getAdvertiserId();
		Integer totalBudget = bean.getTotalBudget();
		ProjectModel model = new ProjectModel();
		model.setId(id);
		model.setAdvertiserId(advertiserId);
		model.setName(name);
		model.setStatus("00");
		model.setTotalBudget(totalBudget);
		String kpiId = bean.getKpiId();
		// project-kpi 添加关联表数据
		ProjectKpiModel kpiModel = new ProjectKpiModel();
		kpiModel.setId(UUID.randomUUID().toString());
		Integer value = bean.getValue();
		kpiModel.setKpiId(kpiId);
		kpiModel.setProjectId(id);
		kpiModel.setValue(value);
		projectKpiMapper.insertSelective(kpiModel);

		// 添加项目信息
		num = projectMapper.insertSelective(model);
		if (num > 0) {
			return id;
		} else {
			return "项目创建失败";
		}
	}
	
	/**
	 * 编辑项目
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateProject(ProjectBean bean) throws Exception{
		int num;
		String id = bean.getId();
		String name = bean.getName();
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
		List<ProjectModel> list = projectMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return "项目名称重复";
		}
		Integer totalBudget = bean.getTotalBudget();
		String status = bean.getStatus();
		String advertiserId = bean.getAdvertiserId();
		ProjectModel model = new ProjectModel();
		model.setId(id);
		model.setName(name);
		model.setStatus(status);
		model.setAdvertiserId(advertiserId );
		model.setTotalBudget(totalBudget);
		String kpiId = bean.getKpiId();
		Integer value = bean.getValue();
		ProjectKpiModel pkModel = new ProjectKpiModel();
		pkModel.setValue(value);
		pkModel.setKpiId(kpiId);
		ProjectKpiModelExample pkExample = new ProjectKpiModelExample();
		pkExample.createCriteria().andProjectIdEqualTo(id);
		projectKpiMapper.updateByExampleSelective(pkModel, pkExample);
		// 修改项目信息
		num = projectMapper.updateByPrimaryKeySelective(model);
		if (num > -1) {
			return id;
		} else {
			return "项目编辑失败";
		}
	}
	
	/**
	 * 删除项目
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteProject(String projectId) throws Exception {
		//查询出项目下活动
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		List<CampaignModel> list = campaignMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			for (CampaignModel model : list) {
				String campaignId = model.getId();
				// 执行活动删除方法
				campaignService.deleteCampaign(campaignId);
			}
		}
		//删除项目——KPI指标 关联关系
		ProjectKpiModelExample pkExample = new ProjectKpiModelExample();
		pkExample.createCriteria().andProjectIdEqualTo(projectId);
		projectKpiMapper.deleteByExample(pkExample);
		int num = projectMapper.deleteByPrimaryKey(projectId);
		return num;
	}

    public ProjectBean selectProject(String id)
    {
        ProjectModel model = projectMapper.selectByPrimaryKey(id);
        ProjectBean projectBean = new ProjectBean();
        ProjectKpiModelExample pkExample = new ProjectKpiModelExample();
        pkExample.createCriteria().andProjectIdEqualTo(id);
        List<ProjectKpiModel> list = projectKpiMapper.selectByExample(pkExample);
        if(list!=null && !list.isEmpty()){
            for(ProjectKpiModel m : list){
                projectBean.setKpiId(m.getId());
                projectBean.setValue(m.getValue());
            }
        }
        projectBean.setAdvertiserId(model.getAdvertiserId());
        projectBean.setId(model.getId());
        projectBean.setName(model.getName());
        projectBean.setRemark(model.getRemark());
        projectBean.setStatus(model.getStatus());
        projectBean.setTotalBudget(model.getTotalBudget());
        
        return projectBean;
    }
	
}
