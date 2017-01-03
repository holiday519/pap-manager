package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.NetworkTargetModel;
import com.pxene.pap.domain.model.NetworkTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NetworkTargetDao {
    long countByExample(NetworkTargetModelExample example);

    int deleteByExample(NetworkTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(NetworkTargetModel record);

    int insertSelective(NetworkTargetModel record);

    List<NetworkTargetModel> selectByExample(NetworkTargetModelExample example);

    NetworkTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") NetworkTargetModel record, @Param("example") NetworkTargetModelExample example);

    int updateByExample(@Param("record") NetworkTargetModel record, @Param("example") NetworkTargetModelExample example);

    int updateByPrimaryKeySelective(NetworkTargetModel record);

    int updateByPrimaryKey(NetworkTargetModel record);
}