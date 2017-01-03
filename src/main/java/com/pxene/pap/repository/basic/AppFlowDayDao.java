package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AppFlowDayModel;
import com.pxene.pap.domain.models.AppFlowDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppFlowDayDao {
    long countByExample(AppFlowDayModelExample example);

    int deleteByExample(AppFlowDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppFlowDayModel record);

    int insertSelective(AppFlowDayModel record);

    List<AppFlowDayModel> selectByExample(AppFlowDayModelExample example);

    AppFlowDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppFlowDayModel record, @Param("example") AppFlowDayModelExample example);

    int updateByExample(@Param("record") AppFlowDayModel record, @Param("example") AppFlowDayModelExample example);

    int updateByPrimaryKeySelective(AppFlowDayModel record);

    int updateByPrimaryKey(AppFlowDayModel record);
}