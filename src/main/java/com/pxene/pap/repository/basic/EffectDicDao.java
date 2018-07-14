package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.EffectDicModel;
import com.pxene.pap.domain.models.EffectDicModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EffectDicDao {
    long countByExample(EffectDicModelExample example);

    int deleteByExample(EffectDicModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(EffectDicModel record);

    int insertSelective(EffectDicModel record);

    List<EffectDicModel> selectByExample(EffectDicModelExample example);

    EffectDicModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EffectDicModel record, @Param("example") EffectDicModelExample example);

    int updateByExample(@Param("record") EffectDicModel record, @Param("example") EffectDicModelExample example);

    int updateByPrimaryKeySelective(EffectDicModel record);

    int updateByPrimaryKey(EffectDicModel record);
}