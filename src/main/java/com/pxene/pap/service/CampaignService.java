package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.repository.CampaignDao;
import com.pxene.pap.web.controller.SysUserController;

@Service
public class CampaignService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private CampaignDao campaignDao;
	
	@Transactional
	public String createCampaign(CampaignBean bean){
		
		int basicNum;
		try {
			String name = bean.getName();
			if (name == null || "".equals(name)) {
				return "活动名称不能为空";
			}
			int nameAndId = campaignDao.selectCampaignByNameAndId(name, null);
			if (nameAndId > 0) {
				return "活动名称重复";
			}
			String id = UUID.randomUUID().toString();
			bean.setId(id);
			bean.setStatus("00");
			bean.setStartDate(new Date());
			bean.setEndDate(new Date());
			basicNum = campaignDao.createCampaignBasic(bean);
			campaignDao.createCampaignTarget(bean);
			campaignDao.createCampaignFrequency(bean);
			if (basicNum > 0) {
				return id;
			}
		} catch (Exception e) {
			LOGGER.error("活动创建失败：",e.getMessage());
		}
		return "活动创建失败";
	}
	 
}
