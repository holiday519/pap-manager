package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.KpiModel;
import com.pxene.pap.domain.model.basic.KpiModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface KpiModelMapper {
    int countByExample(KpiModelExample example);

    int deleteByExample(KpiModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(KpiModel record);

    int insertSelective(KpiModel record);

    List<KpiModel> selectByExample(KpiModelExample example);

    KpiModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") KpiModel record, @Param("example") KpiModelExample example);

    int updateByExample(@Param("record") KpiModel record, @Param("example") KpiModelExample example);

    int updateByPrimaryKeySelective(KpiModel record);

    int updateByPrimaryKey(KpiModel record);
}