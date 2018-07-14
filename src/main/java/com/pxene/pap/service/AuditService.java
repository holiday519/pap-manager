package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageMaterialDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public abstract class AuditService extends BaseService {
	
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
	@Autowired
	protected AdvertiserAuditDao advertiserAuditDao;
	@Autowired
	protected CreativeAuditDao creativeAuditDao;
	@Autowired
	protected ImageMaterialDao imageMaterialDao;
	
	protected Environment env;
	
	@Autowired
    public AuditService(Environment env) {
        this.env = env;
	}
	
	public abstract void auditAdvertiser(String auditId) throws Exception;

	public abstract void synchronizeAdvertiser(String auditId) throws Exception;

	public abstract void auditCreative(String creativeId) throws Exception;

	public abstract void synchronizeCreative(String creativeId) throws Exception;
	
}
