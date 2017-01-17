package com.pxene.pap.repository.custom;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreativeDataHourStatsDao {

	List<Map<String, Object>> selectByCampaignId(@Param("campaignId") String campaignId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
	
	/**
	 * 查询活动的时段投放数据
	 * @param campaignId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<Map<String, Object>> selectTimeDataByCampaignId(@Param("campaignId") String campaignId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
	
}
