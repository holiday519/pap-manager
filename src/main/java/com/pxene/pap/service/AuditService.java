package com.pxene.pap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public abstract class AuditService extends BaseService {
	
	private static final Map<String, AuditService> CACHE = new HashMap<String, AuditService>();
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
		if (CACHE.containsKey(adxId)) {
			return CACHE.get(adxId);
		}
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			MomoAuditService service = new MomoAuditService();
			CACHE.put(adxId, service);
			return service;
		}
		throw new IllegalArgumentException(PhrasesConstant.ADX_NOT_FOUND);
	}

}
