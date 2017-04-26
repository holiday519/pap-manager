package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
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
	
	protected Environment env;
	
	@Autowired
    public AuditService(Environment env) {
        this.env = env;
	}
	
	@Autowired
	private MomoAuditService auditService;

	public abstract void auditAdvertiser(String advertiserId) throws Exception;

	public abstract void synchronizeAdvertiser(String advertiserId) throws Exception;

	public abstract void auditCreative(String creativeId) throws Exception;

	public abstract void synchronizeCreative(String creativeId) throws Exception;
	
	public AuditService newInstance(String adxId) {
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			return auditService;
		}
		throw new IllegalArgumentException(PhrasesConstant.ADX_NOT_FOUND);
	}

}
