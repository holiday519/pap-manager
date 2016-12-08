package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.InfoflowModel;
import com.pxene.pap.domain.model.basic.InfoflowModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InfoflowModelMapper {
    int countByExample(InfoflowModelExample example);

    int deleteByExample(InfoflowModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(InfoflowModel record);

    int insertSelective(InfoflowModel record);

    List<InfoflowModel> selectByExample(InfoflowModelExample example);

    InfoflowModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InfoflowModel record, @Param("example") InfoflowModelExample example);

    int updateByExample(@Param("record") InfoflowModel record, @Param("example") InfoflowModelExample example);

    int updateByPrimaryKeySelective(InfoflowModel record);

    int updateByPrimaryKey(InfoflowModel record);
}