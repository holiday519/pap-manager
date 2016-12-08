package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.PurposeModel;
import com.pxene.pap.domain.model.basic.PurposeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurposeModelMapper {
    int countByExample(PurposeModelExample example);

    int deleteByExample(PurposeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(PurposeModel record);

    int insertSelective(PurposeModel record);

    List<PurposeModel> selectByExample(PurposeModelExample example);

    PurposeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PurposeModel record, @Param("example") PurposeModelExample example);

    int updateByExample(@Param("record") PurposeModel record, @Param("example") PurposeModelExample example);

    int updateByPrimaryKeySelective(PurposeModel record);

    int updateByPrimaryKey(PurposeModel record);
}