package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.IndusttryKpiModel;
import com.pxene.pap.domain.model.basic.IndusttryKpiModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IndustryKpiModelMapper {
    int countByExample(IndusttryKpiModelExample example);

    int deleteByExample(IndusttryKpiModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IndusttryKpiModel record);

    int insertSelective(IndusttryKpiModel record);

    List<IndusttryKpiModel> selectByExample(IndusttryKpiModelExample example);

    IndusttryKpiModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IndusttryKpiModel record, @Param("example") IndusttryKpiModelExample example);

    int updateByExample(@Param("record") IndusttryKpiModel record, @Param("example") IndusttryKpiModelExample example);

    int updateByPrimaryKeySelective(IndusttryKpiModel record);

    int updateByPrimaryKey(IndusttryKpiModel record);
}