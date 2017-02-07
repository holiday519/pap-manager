package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.RuleLogBean;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AppRuleModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.RegionRuleModel;
import com.pxene.pap.domain.models.RuleLogModel;
import com.pxene.pap.domain.models.RuleLogModelExample;
import com.pxene.pap.domain.models.TimeRuleModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppRuleDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.RegionRuleDao;
import com.pxene.pap.repository.basic.RuleLogDao;
import com.pxene.pap.repository.basic.TimeRuleDao;

@Service
public class RuleLogService extends BaseService {

	@Autowired
	private RuleLogDao ruleLogDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private AppRuleDao appRuleDao;
	
	@Autowired
	private RegionRuleDao regionRuleDao;
	
	@Autowired
	private TimeRuleDao timeRuleDao;

	/**
	 * 添加日志
	 */
	@Transactional
	public void createRuleLog(RuleLogBean bean) throws Exception {
		RuleLogModel model = modelMapper.map(bean, RuleLogModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			ruleLogDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(model, bean);
	}

	/**
	 * 查询规则日志列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<RuleLogBean> selectRuleLogs() throws Exception {
		RuleLogModelExample example = new RuleLogModelExample();
		
		List<RuleLogBean> list = new ArrayList<RuleLogBean>();
		List<RuleLogModel> models = ruleLogDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (RuleLogModel model : models) {
			RuleLogBean ruleLogBean = modelMapper.map(model, RuleLogBean.class);
			String campaignId = ruleLogBean.getCampaignId();
			if (!StringUtils.isEmpty(campaignId)) {
				CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
				ruleLogBean.setCampaignName(campaignModel.getName());
			}
			String ruleId = model.getRuleId();
			String ruleType = model.getRuleType();
			String ruleName = null;
			if (!StringUtils.isEmpty(ruleId)) {
				if (StatusConstant.CAMPAIGN_RULE_TYPE_APP.equals(ruleType)) {
					AppRuleModel appRuleModel = appRuleDao.selectByPrimaryKey(ruleId);
					ruleName = appRuleModel.getName();
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_TIME.equals(ruleType)) {
					TimeRuleModel timeRuleModel = timeRuleDao.selectByPrimaryKey(ruleId);
					ruleName = timeRuleModel.getName();
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_REGION.equals(ruleType)) {
					RegionRuleModel regionRuleModel = regionRuleDao.selectByPrimaryKey(ruleId);
					ruleName = regionRuleModel.getName();
				} 
				ruleLogBean.setRuleName(ruleName);
			}
			list.add(ruleLogBean);
		}
		
		return list;
	}

}
