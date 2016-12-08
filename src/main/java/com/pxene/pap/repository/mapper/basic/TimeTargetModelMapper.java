package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.TimeTargetModel;
import com.pxene.pap.domain.model.basic.TimeTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TimeTargetModelMapper {
    int countByExample(TimeTargetModelExample example);

    int deleteByExample(TimeTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(TimeTargetModel record);

    int insertSelective(TimeTargetModel record);

    List<TimeTargetModel> selectByExample(TimeTargetModelExample example);

    TimeTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TimeTargetModel record, @Param("example") TimeTargetModelExample example);

    int updateByExample(@Param("record") TimeTargetModel record, @Param("example") TimeTargetModelExample example);

    int updateByPrimaryKeySelective(TimeTargetModel record);

    int updateByPrimaryKey(TimeTargetModel record);
}