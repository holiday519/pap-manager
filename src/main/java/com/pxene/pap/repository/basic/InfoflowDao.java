package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.InfoflowModel;
import com.pxene.pap.domain.model.InfoflowModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface InfoflowDao {
    long countByExample(InfoflowModelExample example);

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