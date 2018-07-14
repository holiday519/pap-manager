package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.KpiModel;
import com.pxene.pap.domain.models.KpiModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KpiDao {
    long countByExample(KpiModelExample example);

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