package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.pxene.pap.domain.beans.RuleBean;
import com.pxene.pap.domain.model.basic.AppRuleModel;
import com.pxene.pap.domain.model.basic.CampaignRuleModel;
import com.pxene.pap.domain.model.basic.CampaignRuleModelExample;
import com.pxene.pap.domain.model.basic.CampaignRuleModelExample.Criteria;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppRuleDao;
import com.pxene.pap.repository.basic.CampaignRuleDao;

public class AppRuleService extends BaseService {
	
	@Autowired
	private AppRuleDao appRuleDao;
	@Autowired
	private CampaignRuleDao campaignRuleDao;

	public void saveAppRule(RuleBean ruleBean) throws Exception {
		AppRuleModel model = modelMapper.map(ruleBean, AppRuleModel.class);
		model.setId(UUID.randomUUID().toString());
		
		try {
			appRuleDao.insertSelective(model);
        } catch (DuplicateKeyException exception) {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
		
		BeanUtils.copyProperties(model, ruleBean);
	}
	
	public void deleteAppRule(String id) throws Exception {
		AppRuleModel modelInDB = appRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		// 查看该规则下是否绑定了活动
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		Criteria criteria = example.createCriteria();
		criteria.andRuleIdEqualTo(id);
		List<CampaignRuleModel> models = campaignRuleDao.selectByExample(example);
		if (models.size() > 0) {
			// throw
		} else {
			appRuleDao.deleteByPrimaryKey(id);
		}
		
	}
	
	public void updateAppRule(String id, RuleBean ruleBean) throws Exception {
		
	}
	
	public void findAppRuleById(String id) throws Exception {
		appRuleDao.selectByPrimaryKey(id);
	}
}
