package com.pxene.pap.repository.custom;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.AppFlowHourBean;


@Repository
public interface CustomAppFlowHourDao
{
    
    List<AppFlowHourBean> selectTopN(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("limitNum") Integer limitNum);
    
}