package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AdtypeTargetModel;
import com.pxene.pap.domain.model.basic.AdtypeTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdtypeTargetModelMapper {
    int countByExample(AdtypeTargetModelExample example);

    int deleteByExample(AdtypeTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdtypeTargetModel record);

    int insertSelective(AdtypeTargetModel record);

    List<AdtypeTargetModel> selectByExample(AdtypeTargetModelExample example);

    AdtypeTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdtypeTargetModel record, @Param("example") AdtypeTargetModelExample example);

    int updateByExample(@Param("record") AdtypeTargetModel record, @Param("example") AdtypeTargetModelExample example);

    int updateByPrimaryKeySelective(AdtypeTargetModel record);

    int updateByPrimaryKey(AdtypeTargetModel record);
}