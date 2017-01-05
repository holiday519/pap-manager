package com.pxene.pap.repository.custom;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFlowHourStatsDao
{
    
    List<Map<String, Object>> selectTopN(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("limitNum") Integer limitNum);
    
}