package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class PutOnService {

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 按照活动投放
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	public void putOnCampaign(List<String> campaignIds) throws Exception {
		if (campaignIds == null || campaignIds.isEmpty()) {
			throw new NotFoundException();
		}
		for (String campaignId : campaignIds) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			campaignModel.setStatus(StatusConstant.CAMPAIGN_START);
			String projectId = campaignModel.getProjectId();
			ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId );
			
			if (StatusConstant.PROJECT_START.equals(projectModel.getStatus())) {
				//投放
				putOn(campaignId);
				campaignDao.updateByPrimaryKeySelective(campaignModel);
			}
		}
	}

	/**
	 * 按照项目投放
	 * @param projectIds
	 * @throws Exception
	 */
	@Transactional
	public void putOnProject(List<String> projectIds) throws Exception {
		if (projectIds == null || projectIds.isEmpty()) {
			throw new NotFoundException();
		}
		
		for (String projectId : projectIds) {
			ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
			projectModel.setStatus(StatusConstant.PROJECT_START);
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andProjectIdEqualTo(projectId);
			List<CampaignModel> campaigns = campaignDao.selectByExample(example);
			if (campaigns == null || campaigns.isEmpty()) {
				continue;
			}
			for (CampaignModel campaign : campaigns) {
				if (StatusConstant.CAMPAIGN_START.equals(campaign.getStatus())) {
					//投放
					putOn(campaign.getId());
				}
			}
			//项目投放之后修改状态
			projectDao.updateByPrimaryKeySelective(projectModel);
		}
	}
	
	/**
	 * 投放
	 * @param campaignIds
	 * @throws Exception
	 */
	public void putOn(String campaignId) throws Exception {
		//写入活动下的创意基本信息   dsp_mapid_*
		redisService.writeCreativeInfoToRedis(campaignId);
		//写入活动下的创意ID  dsp_group_mapids_*
		redisService.WriteMapidToRedis(campaignId);
		//写入活动基本信息   dsp_group_info_*
		redisService.writeCampaignInfoToRedis(campaignId);
		//写入活动定向   dsp_group_target_*
		redisService.writeCampaignTargetToRedis(campaignId);
		//写入活动ID pap_groupids
		redisService.writeCampaignIds(campaignId);
		//写入活动频次信息   dsp_groupid_frequencycapping_*
//		redisService.writeCampaignFrequencyToRedis(campaignId);
	}
}
