package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.EffectFileModel;
import com.pxene.pap.domain.models.EffectFileModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EffectFileDao {
    long countByExample(EffectFileModelExample example);

    int deleteByExample(EffectFileModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(EffectFileModel record);

    int insertSelective(EffectFileModel record);

    List<EffectFileModel> selectByExample(EffectFileModelExample example);

    EffectFileModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EffectFileModel record, @Param("example") EffectFileModelExample example);

    int updateByExample(@Param("record") EffectFileModel record, @Param("example") EffectFileModelExample example);

    int updateByPrimaryKeySelective(EffectFileModel record);

    int updateByPrimaryKey(EffectFileModel record);
}