package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.MonitorModel;
import com.pxene.pap.domain.model.basic.MonitorModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MonitorModelMapper {
    int countByExample(MonitorModelExample example);

    int deleteByExample(MonitorModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(MonitorModel record);

    int insertSelective(MonitorModel record);

    List<MonitorModel> selectByExample(MonitorModelExample example);

    MonitorModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MonitorModel record, @Param("example") MonitorModelExample example);

    int updateByExample(@Param("record") MonitorModel record, @Param("example") MonitorModelExample example);

    int updateByPrimaryKeySelective(MonitorModel record);

    int updateByPrimaryKey(MonitorModel record);
}