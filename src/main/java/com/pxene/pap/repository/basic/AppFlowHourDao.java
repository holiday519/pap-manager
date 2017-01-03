package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.AppFlowHourModel;
import com.pxene.pap.domain.model.AppFlowHourModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppFlowHourDao {
    long countByExample(AppFlowHourModelExample example);

    int deleteByExample(AppFlowHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppFlowHourModel record);

    int insertSelective(AppFlowHourModel record);

    List<AppFlowHourModel> selectByExample(AppFlowHourModelExample example);

    AppFlowHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppFlowHourModel record, @Param("example") AppFlowHourModelExample example);

    int updateByExample(@Param("record") AppFlowHourModel record, @Param("example") AppFlowHourModelExample example);

    int updateByPrimaryKeySelective(AppFlowHourModel record);

    int updateByPrimaryKey(AppFlowHourModel record);
}