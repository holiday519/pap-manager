package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.IndustryKpiModel;
import com.pxene.pap.domain.model.IndustryKpiModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IndustryKpiDao {
    long countByExample(IndustryKpiModelExample example);

    int deleteByExample(IndustryKpiModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IndustryKpiModel record);

    int insertSelective(IndustryKpiModel record);

    List<IndustryKpiModel> selectByExample(IndustryKpiModelExample example);

    IndustryKpiModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IndustryKpiModel record, @Param("example") IndustryKpiModelExample example);

    int updateByExample(@Param("record") IndustryKpiModel record, @Param("example") IndustryKpiModelExample example);

    int updateByPrimaryKeySelective(IndustryKpiModel record);

    int updateByPrimaryKey(IndustryKpiModel record);
}