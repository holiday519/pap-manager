package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.domain.models.RegionModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RegionDao {
    long countByExample(RegionModelExample example);

    int deleteByExample(RegionModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RegionModel record);

    int insertSelective(RegionModel record);

    List<RegionModel> selectByExample(RegionModelExample example);

    RegionModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RegionModel record, @Param("example") RegionModelExample example);

    int updateByExample(@Param("record") RegionModel record, @Param("example") RegionModelExample example);

    int updateByPrimaryKeySelective(RegionModel record);

    int updateByPrimaryKey(RegionModel record);
}