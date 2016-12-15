package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class PutOnService {

	@Autowired
	private CampaignModelMapper campaignMapper;
	
	@Autowired
	private ProjectModelMapper projectMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Transactional
	public void putOnByCampaign(List<String> campaignIds) throws Exception {
		if (campaignIds == null || campaignIds.isEmpty()) {
			throw new Exception();
		}
		for (String campaignid : campaignIds) {

		}
	}

	@Transactional
	public void putOnByProject(List<String> projectIds) throws Exception {
		if (projectIds == null || projectIds.isEmpty()) {
			throw new Exception();
		}
		for (String projectId : projectIds) {
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andProjectIdEqualTo(projectId);
			List<CampaignModel> campaigns = campaignMapper.selectByExample(example);
			if (campaigns == null || campaigns.isEmpty()) {
				continue;
			}
			List<String> campaignIds = new ArrayList<String>();
			for (CampaignModel campaign : campaigns) {
				campaignIds.add(campaign.getId());
			}
			putOnByCampaign(campaignIds);
		}
	}
	
}
