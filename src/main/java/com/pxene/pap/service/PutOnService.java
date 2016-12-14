package com.pxene.pap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Transactional
	public void putOnByCampaign(String campaignId, List<String> projectIds) throws Exception {
		
	}

	@Transactional
	public void putOnByProject(List<String> projectIds) throws Exception {
		if (projectIds == null || projectIds.isEmpty()) {
			throw new Exception();
		}
		for(String projectId : projectIds){
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andProjectIdEqualTo(projectId);
			List<CampaignModel> campaigns = campaignMapper.selectByExample(example);
			
		}
	}
	
}
