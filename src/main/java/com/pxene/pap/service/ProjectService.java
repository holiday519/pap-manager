package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.model.basic.ProjectKpiModel;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.repository.mapper.basic.ProjectKpiModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectModelMapper projectMapper;
	
	@Autowired
	private ProjectKpiModelMapper projectKpiMapper;
	
	@Transactional
	public String createProject(ProjectBean bean) throws Exception{
		int num;
		String name = bean.getName();
		if (name == null) {
			return "项目名称不能为空";
		}
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
		//project-kpi 添加关联表数据
		ProjectKpiModel kpiModel = new ProjectKpiModel();
		kpiModel.setId(UUID.randomUUID().toString());
		kpiModel.setKpiId(kpiId);
		kpiModel.setProjectId(id);
		projectKpiMapper.insertSelective(kpiModel);
		
		//添加项目信息
		num = projectMapper.insertSelective(model);
		if (num > 0) {
			return id;
		}else{
			return "项目创建失败";
		}
	}
	 
}
