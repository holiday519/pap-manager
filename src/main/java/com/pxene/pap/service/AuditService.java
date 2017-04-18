package com.pxene.pap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.exception.IllegalArgumentException;
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
	
	private static Map<String, AuditService> cache = new HashMap<String, AuditService>();
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

	public abstract void auditAdvertiser(String advertiserId) throws Exception;

	public abstract void synchronizeAdvertiser(String advertiserId) throws Exception;

	public abstract void auditCreative(String creativeId) throws Exception;

	public abstract void synchronizeCreative(String creativeId) throws Exception;
	
	public static AuditService newInstance(String adxId) {
		if (cache.containsKey(adxId)) {
			return cache.get(adxId);
		}
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			MomoAuditService service = new MomoAuditService();
			cache.put(adxId, service);
			return service;
		}
		throw new IllegalArgumentException(PhrasesConstant.ADX_NOT_FOUND);
	}

}
