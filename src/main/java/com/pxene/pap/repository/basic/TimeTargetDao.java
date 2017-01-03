package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.TimeTargetModel;
import com.pxene.pap.domain.model.TimeTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TimeTargetDao {
    long countByExample(TimeTargetModelExample example);

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