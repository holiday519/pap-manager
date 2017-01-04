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

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.RuleBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignRuleModel;
import com.pxene.pap.domain.models.CampaignRuleModelExample;
import com.pxene.pap.domain.models.RegionRuleModel;
import com.pxene.pap.domain.models.RegionRuleModelExample;
import com.pxene.pap.domain.models.CampaignRuleModelExample.Criteria;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CampaignRuleDao;
import com.pxene.pap.repository.basic.RegionRuleDao;

@Service
public class RegionRuleService extends BaseService {
	
	@Autowired
	private RegionRuleDao regionRuleDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CampaignRuleDao campaignRuleDao;
	
	public void saveRegionRule(RuleBean ruleBean) throws Exception {
		RegionRuleModel model = modelMapper.map(ruleBean, RegionRuleModel.class);
		String ruleId = UUID.randomUUID().toString();
		model.setId(ruleId);
		model.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_USED);
		
		try {
			ruleBean.setId(ruleId);
			//添加关联关系
			addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_REGION);
			regionRuleDao.insertSelective(model);
        } catch (DuplicateKeyException exception) {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
		
		BeanUtils.copyProperties(model, ruleBean);
	}
	
	public void deleteRegionRule(String id) throws Exception {
		RegionRuleModel modelInDB = regionRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		// 查看该规则下是否绑定了活动
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		Criteria criteria = example.createCriteria();
		criteria.andRuleIdEqualTo(id);
		List<CampaignRuleModel> models = campaignRuleDao.selectByExample(example);
		
		if (models.size() > 0) {
			throw new IllegalStatusException(PhrasesConstant.RULE_HAVE_CAMPAIGN);
		} else {
			regionRuleDao.deleteByPrimaryKey(id);
		}
		
	}
	
	public void updateRegionRule(String id, RuleBean ruleBean) throws Exception {
		RegionRuleModel modelInDB = regionRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		RegionRuleModel ruleModel = modelMapper.map(ruleBean, RegionRuleModel.class);
		//删除所有关联关系
		deleteCampaignAndRule(id);
		//重新添加关联关系
		ruleBean.setId(id);
		addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_REGION);
		
		try {
			// 修改规则信息
			regionRuleDao.updateByPrimaryKeySelective(ruleModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	public RuleBean findRegionRuleById(String id) throws Exception {
		RegionRuleModel regionRuleModel = regionRuleDao.selectByPrimaryKey(id);
		if(regionRuleModel == null){
			throw new ResourceNotFoundException();
		}
		RuleBean ruleBean = modelMapper.map(regionRuleModel, RuleBean.class);
		RuleBean bean = getParamForRule(ruleBean);
		
		return bean;
	}
	
	public List<RuleBean> listRegionRule(String name) throws Exception {
		RegionRuleModelExample example = new RegionRuleModelExample();
		
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<RegionRuleModel> rules = regionRuleDao.selectByExample(example);
		List<RuleBean> beans = new ArrayList<RuleBean>();
		
		if (rules == null || rules.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (RegionRuleModel model : rules) {
			RuleBean bean = modelMapper.map(model, RuleBean.class);
			beans.add(getParamForRule(bean));
		}
		
    	return beans;
		
	}
	
	/**
	 * 获取活动绑定的活动ID和名称
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RuleBean getParamForRule(RuleBean bean) throws Exception {
		String ruleId = bean.getId();
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		example.createCriteria().andRuleIdEqualTo(ruleId);
		List<CampaignRuleModel> list = campaignRuleDao.selectByExample(example);
		if (list == null) {
			return bean;
		}
		List<String> campaignIds = new ArrayList<String>();
		List<String> campaignNames = new ArrayList<String>();
		for (CampaignRuleModel model : list) {
			String campaignId = model.getCampaignId();
			if (!campaignIds.contains(campaignId)) {
				CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
				if (campaignModel != null) {
					campaignIds.add(campaignId);
					campaignNames.add(campaignModel.getName());
				}
			}
		}
		
		if (!campaignIds.isEmpty()) {
			String[] idArray = (String[]) campaignIds.toArray(new String[campaignIds.size()]);
			String[] nameArray = (String[]) campaignNames.toArray(new String[campaignNames.size()]);
			bean.setCampaignIds(idArray);
			bean.setCampaignNames(nameArray);
		}
		
		return bean;
	}
	
	/**
	 * 添加活动规则关联关系
	 * @param ruleBean
	 * @param type
	 */
	public void addCampaignAndRule(RuleBean ruleBean, String type) throws Exception {
		String[] campaignIds = ruleBean.getCampaignIds();
		if (campaignIds != null && campaignIds.length > 0) {
			for (String campaignId : campaignIds) {
				CampaignRuleModel model = new CampaignRuleModel();
				model.setId(UUID.randomUUID().toString());
				model.setCampaignId(campaignId);
				model.setRuleId(ruleBean.getId());
				model.setRuleType(type);
				campaignRuleDao.insertSelective(model);
			}
		}
	}
	
	/**
	 * 删除活动规则关联关系
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteCampaignAndRule(String ruleId) throws Exception {
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		example.createCriteria().andRuleIdEqualTo(ruleId);
		//删除关联关系
		campaignRuleDao.deleteByExample(example);
	}
}