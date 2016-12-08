package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.RegionTargetModel;
import com.pxene.pap.domain.model.basic.RegionTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RegionTargetModelMapper {
    int countByExample(RegionTargetModelExample example);

    int deleteByExample(RegionTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RegionTargetModel record);

    int insertSelective(RegionTargetModel record);

    List<RegionTargetModel> selectByExample(RegionTargetModelExample example);

    RegionTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RegionTargetModel record, @Param("example") RegionTargetModelExample example);

    int updateByExample(@Param("record") RegionTargetModel record, @Param("example") RegionTargetModelExample example);

    int updateByPrimaryKeySelective(RegionTargetModel record);

    int updateByPrimaryKey(RegionTargetModel record);
}