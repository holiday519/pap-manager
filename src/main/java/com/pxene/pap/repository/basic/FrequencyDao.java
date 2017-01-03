package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.FrequencyModel;
import com.pxene.pap.domain.model.FrequencyModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface FrequencyDao {
    long countByExample(FrequencyModelExample example);

    int deleteByExample(FrequencyModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(FrequencyModel record);

    int insertSelective(FrequencyModel record);

    List<FrequencyModel> selectByExample(FrequencyModelExample example);

    FrequencyModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FrequencyModel record, @Param("example") FrequencyModelExample example);

    int updateByExample(@Param("record") FrequencyModel record, @Param("example") FrequencyModelExample example);

    int updateByPrimaryKeySelective(FrequencyModel record);

    int updateByPrimaryKey(FrequencyModel record);
}