package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.EffectModel;
import com.pxene.pap.domain.models.EffectModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EffectDao {
    long countByExample(EffectModelExample example);

    int deleteByExample(EffectModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(EffectModel record);

    int insertSelective(EffectModel record);

    List<EffectModel> selectByExample(EffectModelExample example);

    EffectModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EffectModel record, @Param("example") EffectModelExample example);

    int updateByExample(@Param("record") EffectModel record, @Param("example") EffectModelExample example);

    int updateByPrimaryKeySelective(EffectModel record);

    int updateByPrimaryKey(EffectModel record);
}