package com.pxene.pap.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.repository.CreativeDao;
import com.pxene.pap.web.controller.SysUserController;

@Service
public class CreativeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private CreativeDao creativeDao;
	
	@Transactional
	public String createCreative(CreativeBean bean){
		
		int num;
		try {
			String name = bean.getName();
			if (name == null || "".equals(name)) {
				return "创意名称不能为空";
			}
			String id = UUID.randomUUID().toString();
			bean.setId(id);
			num = creativeDao.createCreative(bean);
			if (num > 0) {
				return id;
			}
		} catch (Exception e) {
			LOGGER.error("创意创建失败：",e.getMessage());
		}
		return "创意创建失败";
	}
	 
}
