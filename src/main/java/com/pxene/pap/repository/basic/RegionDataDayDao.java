package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RegionDataDayModel;
import com.pxene.pap.domain.models.RegionDataDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RegionDataDayDao {
    long countByExample(RegionDataDayModelExample example);

    int deleteByExample(RegionDataDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionDataDayModel record);

    int insertSelective(RegionDataDayModel record);

    List<RegionDataDayModel> selectByExample(RegionDataDayModelExample example);

    RegionDataDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionDataDayModel record, @Param("example") RegionDataDayModelExample example);

    int updateByExample(@Param("record") RegionDataDayModel record, @Param("example") RegionDataDayModelExample example);

    int updateByPrimaryKeySelective(RegionDataDayModel record);

    int updateByPrimaryKey(RegionDataDayModel record);
}