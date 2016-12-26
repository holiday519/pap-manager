package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PutOnService extends BaseService{
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 投放
	 * @param campaignIds
	 * @throws Exception
	 */
	public void launch(String campaignId) throws Exception {
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
	
	/**
	 * 暂停
	 * @param campaignId
	 * @throws Exception
	 */
	public void pause(String campaignId) throws Exception {
		//从redis中删除投放中的活动id
		redisService.deleteCampaignId(campaignId);
	}
}
