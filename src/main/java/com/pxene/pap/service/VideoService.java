package com.pxene.pap.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.repository.VideoDao;
import com.pxene.pap.web.controller.SysUserController;

@Service
public class VideoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private VideoDao VideoDao;
	
	@Transactional
	public String updateVideo(VideoBean bean){
		
		int num;
		try {
			num = VideoDao.updateVideo(bean);
			if (num > 0) {
				return bean.getUuid();
			}
		} catch (Exception e) {
			LOGGER.error("操作失败：",e.getMessage());
		}
		return "操作失败";
	}
	 
}
