package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RegionDataHourModel;
import com.pxene.pap.domain.models.RegionDataHourModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RegionDataHourDao {
    long countByExample(RegionDataHourModelExample example);

    int deleteByExample(RegionDataHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionDataHourModel record);

    int insertSelective(RegionDataHourModel record);

    List<RegionDataHourModel> selectByExample(RegionDataHourModelExample example);

    RegionDataHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionDataHourModel record, @Param("example") RegionDataHourModelExample example);

    int updateByExample(@Param("record") RegionDataHourModel record, @Param("example") RegionDataHourModelExample example);

    int updateByPrimaryKeySelective(RegionDataHourModel record);

    int updateByPrimaryKey(RegionDataHourModel record);
}