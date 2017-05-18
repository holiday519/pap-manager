package com.pxene.pap.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.IndustryDao;

/**
 * 汽车之家的审核同步 
 * @author lizhuoling
 *
 */
@Service
public class AutohomeAuditService extends AuditService {
	
	@Autowired
	private IndustryDao industryDao;

	public AutohomeAuditService(Environment env) {
		super(env);
	}

	/**
	 * 审核广告主ADX（更新广告主审核表数据）
	 */
	@Override
	@Transactional
	public void auditAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			// 如果广告主审核信息不为空，则修改广告审核的状态：审核中
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
			// 更新数据库信息
			advertiserAuditDao.updateByPrimaryKeySelective(advertiserAudit);
		}

	}

	/**
	 * 同步广告主ADX（更新广告主审核表状态）
	 */
	@Override
	@Transactional
	public void synchronizeAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			// 如果广告主审核信息不为空，则修改广告主审核表状态：审核通过
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
			// 更新广告主审核表数据
			advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
		}
	}

	/**
	 * 审核创意（提交汽车之家审核）
	 */
	@Override
	@Transactional	
	public void auditCreative(String creativeId) throws Exception {
		
		// 查询创意信息
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		// 判断创意是否存在
		if (creativeModel == null) {
			throw new ResourceNotFoundException();
		}
		// 根据ADXId查询汽车之家的ADX信息
		AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_AUTOHOME_VALUE);

		// 发送审核需要的参数
		// 1.dspId
		String dspId = adx.getDspId();
		// 2.dspName
		String dspName = adx.getDspName();
		// 3.获取当前时间
		long timestamp = System.currentTimeMillis();
		// 4.生成签名
		String sign = GlobalUtil.MD5("appKey" + dspId + "crid" + creativeId + "timestamp" + timestamp);

		// 活动id
		String campaignId = creativeModel.getCampaignId();
		// 根据活动id查询项目id
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		// 根据项目id查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String strAdvertiserId = projectModel.getAdvertiserId();
		// 根据广告主id查询广告主
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(strAdvertiserId);

		// 发送审核需要的参数
		// 5.广告主id
		int advertiserId = Integer.parseInt(strAdvertiserId);
		// 6.广告主名称
		String advertiserName = advertiserModel.getName();
		// 7.行业id
		String industryId = advertiserModel.getIndustryId();
		// 8.行业名称
		IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
		String industryName = industryModel.getName();
		// 创意素材类型
		String creativeType = creativeModel.getType();
		
		Date current = new Date();
    	CreativeAuditModelExample creativeExample = new CreativeAuditModelExample();
    	creativeExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_INMOBI_VALUE);
    	List<CreativeAuditModel> creativeAuditList = creativeAuditDao.selectByExample(creativeExample);
		if (creativeAuditList != null && !creativeAuditList.isEmpty()) {
			// 如果创意审核表信息不为空，更新状态和时间
			for (CreativeAuditModel creativeAuditModel : creativeAuditList) {
				creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
				creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
				creativeAuditDao.updateByPrimaryKeySelective(creativeAuditModel);
			}
		} else {
			// 如果创意审核表信息为空，添加加入相关信息
			CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
			creativeAuditModel.setId(UUIDGenerator.getUUID());
			creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
			creativeAuditModel.setAdxId(AdxKeyConstant.ADX_INMOBI_VALUE);
			creativeAuditModel.setCreativeId(creativeId);
			creativeAuditDao.insertSelective(creativeAuditModel);
		}    	
		
	}

	/**
	 * 同步创意（同步汽车之家审核的结果）
	 */
	@Override
	@Transactional
	public void synchronizeCreative(String creativeId) throws Exception{
		CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
		creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_INMOBI_VALUE);
		CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
		creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
		creativeAuditDao.updateByExampleSelective(creativeAuditModel, creativeAuditExample);
	}
	
}
