package com.pxene.pap.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;

/**
 * Inmobi审核同步
 * @author lizhuoling
 *
 */
@Service
public class InmobiAuditService extends AuditService {

	public InmobiAuditService(Environment env) {
		super(env);
	}

	/**
	 * 审核广告主（更新广告主审核表数据）
	 */
	@Override
	@Transactional
	public void auditAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			// 如果广告主审核信息不为空，则修改广告审核的状态
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
			// 更新数据库信息
			advertiserAuditDao.updateByPrimaryKeySelective(advertiserAudit);
		}

	}

	/**
	 * 同步广告主（更新广告主审核表状态）
	 */
	@Override
	@Transactional
	public void synchronizeAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			// 如果广告主审核信息不为空，则修改广告主审核表状态
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
			// 更新广告主审核表数据
			advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
		}
	}

	/**
	 * 审核创意（提交陌陌审核）
	 */
	@Override
	@Transactional	
	public void auditCreative(String creativeId) throws Exception {
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
	 * 同步创意（同步陌陌审核信息）
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
