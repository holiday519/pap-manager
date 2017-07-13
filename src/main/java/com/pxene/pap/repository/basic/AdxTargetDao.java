package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdxTargetModel;
import com.pxene.pap.domain.models.AdxTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdxTargetDao {
    long countByExample(AdxTargetModelExample example);

    int deleteByExample(AdxTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdxTargetModel record);

    int insertSelective(AdxTargetModel record);

    List<AdxTargetModel> selectByExample(AdxTargetModelExample example);

    AdxTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdxTargetModel record, @Param("example") AdxTargetModelExample example);

    int updateByExample(@Param("record") AdxTargetModel record, @Param("example") AdxTargetModelExample example);

    int updateByPrimaryKeySelective(AdxTargetModel record);

    int updateByPrimaryKey(AdxTargetModel record);
}