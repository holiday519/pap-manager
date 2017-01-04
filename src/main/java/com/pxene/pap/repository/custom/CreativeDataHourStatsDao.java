package com.pxene.pap.repository.custom;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreativeDataHourStatsDao {

	List<Map<String, Object>> selectByCampaignId(@Param("campaignId") String campaignId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
