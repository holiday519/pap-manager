package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdTypeTargetModel;
import com.pxene.pap.domain.models.AdTypeTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdTypeTargetDao {
    long countByExample(AdTypeTargetModelExample example);

    int deleteByExample(AdTypeTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdTypeTargetModel record);

    int insertSelective(AdTypeTargetModel record);

    List<AdTypeTargetModel> selectByExample(AdTypeTargetModelExample example);

    AdTypeTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdTypeTargetModel record, @Param("example") AdTypeTargetModelExample example);

    int updateByExample(@Param("record") AdTypeTargetModel record, @Param("example") AdTypeTargetModelExample example);

    int updateByPrimaryKeySelective(AdTypeTargetModel record);

    int updateByPrimaryKey(AdTypeTargetModel record);
}