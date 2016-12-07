package com.pxene.pap.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.repository.ProjectDao;
import com.pxene.pap.web.controller.SysUserController;

@Service
public class ProjectService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private ProjectDao projectDao;
	
	@Transactional
	public String createProject(ProjectBean bean){
		
		int num;
		try {
			String name = bean.getName();
			if (name == null) {
				return "项目名称不能为空";
			}
			int nameAndId = projectDao.selectProjectByNameAndId(name, null);
			if (nameAndId > 0) {
				return "项目名称重复";
			}
			String id = UUID.randomUUID().toString();
			bean.setId(id);
			bean.setStatus("00");
			num = projectDao.createProject(bean);
			if (num > 0) {
				return id;
			}
		} catch (Exception e) {
			LOGGER.error("项目创建失败：",e.getMessage());
		}
		return "项目创建失败";
	}
	 
}
