package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RegionFlowDayModel;
import com.pxene.pap.domain.models.RegionFlowDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RegionFlowDayDao {
    long countByExample(RegionFlowDayModelExample example);

    int deleteByExample(RegionFlowDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionFlowDayModel record);

    int insertSelective(RegionFlowDayModel record);

    List<RegionFlowDayModel> selectByExample(RegionFlowDayModelExample example);

    RegionFlowDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionFlowDayModel record, @Param("example") RegionFlowDayModelExample example);

    int updateByExample(@Param("record") RegionFlowDayModel record, @Param("example") RegionFlowDayModelExample example);

    int updateByPrimaryKeySelective(RegionFlowDayModel record);

    int updateByPrimaryKey(RegionFlowDayModel record);
}