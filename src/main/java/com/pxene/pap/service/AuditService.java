package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.ProjectDao;

public abstract class AuditService {
	
	@Autowired
	protected AdxDao adxDao;
	@Autowired
	protected CreativeDao creativeDao;
	@Autowired
	protected InfoflowMaterialDao infoflowMaterialDao;
	@Autowired
	protected ImageDao imageDao;
	@Autowired
	protected CampaignDao campaignDao;
	@Autowired
	protected LandpageDao landpageDao;
	@Autowired
	protected ProjectDao projectDao;
	@Autowired
	protected AdvertiserDao advertiserDao;
	@Autowired
	protected IndustryAdxDao industryAdxDao;

	public abstract void auditAdvertiser() throws Exception;

	public abstract void synchronizeAdvertiser() throws Exception;

	public abstract void auditCreative(String creativeId) throws Exception;

	public abstract void synchronizeCreative(String creativeId) throws Exception;
	
	public static AuditService newInstance(int adxId) {
		return null;
	}

}
