package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.pxene.pap.domain.beans.RuleBean.Condition;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignRuleModel;
import com.pxene.pap.domain.models.CampaignRuleModelExample;
import com.pxene.pap.domain.models.CampaignRuleModelExample.Criteria;
import com.pxene.pap.domain.models.CreativeRuleModel;
import com.pxene.pap.domain.models.CreativeRuleModelExample;
import com.pxene.pap.domain.models.RuleConditionModel;
import com.pxene.pap.domain.models.RuleConditionModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CampaignRuleDao;
import com.pxene.pap.repository.basic.CreativeRuleDao;
import com.pxene.pap.repository.basic.RuleConditionDao;

@Service
public class CreativeRuleService extends BaseService {
	
	@Autowired
	private CreativeRuleDao creativeRuleDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private RuleConditionDao ruleConditionDao;
	
	@Autowired
	private CampaignRuleDao campaignRuleDao;
	
	public void saveCreativeRule(RuleBean ruleBean) throws Exception {
		CreativeRuleModel model = modelMapper.map(ruleBean, CreativeRuleModel.class);
		String ruleId = UUID.randomUUID().toString();
		model.setId(ruleId);
		model.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_USED);
		
		try {
			ruleBean.setId(ruleId);
			//添加关联关系
			addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_CREATIVE);
			//添加规则——条件
			addRuleCondition(ruleBean);
			creativeRuleDao.insertSelective(model);
        } catch (DuplicateKeyException exception) {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
		
		BeanUtils.copyProperties(model, ruleBean);
	}
	
	public void deleteCreativeRule(String id) throws Exception {
		CreativeRuleModel modelInDB = creativeRuleDao.selectByPrimaryKey(id);
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
			//删除规则条件
			deleteRuleConditionById(id);
			creativeRuleDao.deleteByPrimaryKey(id);
		}
		
	}
	
	public void updateCreativeRule(String id, RuleBean ruleBean) throws Exception {
		CreativeRuleModel modelInDB = creativeRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		CreativeRuleModel ruleModel = modelMapper.map(ruleBean, CreativeRuleModel.class);
		//删除所有关联关系
		deleteCampaignAndRule(id);
		//删除规则条件
		deleteRuleConditionById(id);
		//重新添加规则——条件
		addRuleCondition(ruleBean);
		//重新添加关联关系
		ruleBean.setId(id);
		addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_CREATIVE);
		
		try {
			// 修改规则信息
			creativeRuleDao.updateByPrimaryKeySelective(ruleModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	public RuleBean findCreativeRuleById(String id) throws Exception {
		CreativeRuleModel creativeRuleModel = creativeRuleDao.selectByPrimaryKey(id);
		if(creativeRuleModel == null){
			throw new ResourceNotFoundException();
		}
		RuleBean ruleBean = modelMapper.map(creativeRuleModel, RuleBean.class);
		RuleBean bean = getParamForRule(ruleBean);
		
		return bean;
	}
	
	public List<RuleBean> listCreativeRule(String name) throws Exception {
		CreativeRuleModelExample example = new CreativeRuleModelExample();
		
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<CreativeRuleModel> rules = creativeRuleDao.selectByExample(example);
		List<RuleBean> beans = new ArrayList<RuleBean>();
		
		if (rules == null || rules.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (CreativeRuleModel model : rules) {
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
		//查询出规则条件
		List<Condition> conditionList = new ArrayList<Condition>();
		
		RuleConditionModelExample rcExample = new RuleConditionModelExample();
		rcExample.createCriteria().andRuleIdEqualTo(ruleId);
		List<RuleConditionModel> conditions = ruleConditionDao.selectByExample(rcExample);
		if (conditions != null && !conditions.isEmpty()) {
			for (RuleConditionModel mod : conditions) {
				Condition model = modelMapper.map(mod, Condition.class);
				conditionList.add(model);
			}
		}
		if (!conditionList.isEmpty()) {
			Condition[] cds = new Condition[conditionList.size()];
			for (int i=0;i< conditionList.size();i++) {
				cds[i] = conditionList.get(i);
			}
			bean.setConditions(cds);
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
	
	/**
	 * 修改规则状态
	 * @param id
	 * @param map
	 * @throws Exception
	 */
	public void updateCreativeRuleStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		CreativeRuleModel ruleModel = creativeRuleDao.selectByPrimaryKey(id);
		if (ruleModel == null) {
			throw new ResourceNotFoundException();
		}
		
		String action = map.get("action").toString();
		String status = null;
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			status = StatusConstant.CAMPAIGN_RULE_STATUS_UNUSED;
			
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
			status = StatusConstant.CAMPAIGN_RULE_STATUS_USED;
			
		}else {
			throw new IllegalStatusException();
		}
		ruleModel.setStatus(status);
		creativeRuleDao.updateByPrimaryKeySelective(ruleModel);
	}
	/**
	 * 向规则——条件表插入数据
	 * @param ruleBean
	 * @throws Exception
	 */
	public void addRuleCondition(RuleBean ruleBean) throws Exception {
		Condition[] conditions = ruleBean.getConditions();
		if (conditions != null && conditions.length > 0) {
			for (Condition condition : conditions) {
				RuleConditionModel ruleCondition = modelMapper.map(condition, RuleConditionModel.class);
				ruleCondition.setId(UUID.randomUUID().toString());
				ruleCondition.setRuleId(ruleBean.getId());
				ruleConditionDao.insertSelective(ruleCondition);
			}
		}
	}
	
	/**
	 * 删除规则——条件表数据
	 * @param ruleId
	 */
	public void deleteRuleConditionById(String ruleId) {
		if (!StringUtils.isEmpty(ruleId)) {
			RuleConditionModelExample example = new RuleConditionModelExample();
			example.createCriteria().andRuleIdEqualTo(ruleId);
			ruleConditionDao.deleteByExample(example );
		}
	}
}
