package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.OperatorTargetModel;
import com.pxene.pap.domain.model.basic.OperatorTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperatorTargetModelMapper {
    int countByExample(OperatorTargetModelExample example);

    int deleteByExample(OperatorTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(OperatorTargetModel record);

    int insertSelective(OperatorTargetModel record);

    List<OperatorTargetModel> selectByExample(OperatorTargetModelExample example);

    OperatorTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OperatorTargetModel record, @Param("example") OperatorTargetModelExample example);

    int updateByExample(@Param("record") OperatorTargetModel record, @Param("example") OperatorTargetModelExample example);

    int updateByPrimaryKeySelective(OperatorTargetModel record);

    int updateByPrimaryKey(OperatorTargetModel record);
}