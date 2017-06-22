package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.StaticvalModel;
import com.pxene.pap.domain.models.StaticvalModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaticvalDao {
    long countByExample(StaticvalModelExample example);

    int deleteByExample(StaticvalModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(StaticvalModel record);

    int insertSelective(StaticvalModel record);

    List<StaticvalModel> selectByExample(StaticvalModelExample example);

    StaticvalModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StaticvalModel record, @Param("example") StaticvalModelExample example);

    int updateByExample(@Param("record") StaticvalModel record, @Param("example") StaticvalModelExample example);

    int updateByPrimaryKeySelective(StaticvalModel record);

    int updateByPrimaryKey(StaticvalModel record);
}