package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.StaticModel;
import com.pxene.pap.domain.models.StaticModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaticDao {
    long countByExample(StaticModelExample example);

    int deleteByExample(StaticModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(StaticModel record);

    int insertSelective(StaticModel record);

    List<StaticModel> selectByExample(StaticModelExample example);

    StaticModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StaticModel record, @Param("example") StaticModelExample example);

    int updateByExample(@Param("record") StaticModel record, @Param("example") StaticModelExample example);

    int updateByPrimaryKeySelective(StaticModel record);

    int updateByPrimaryKey(StaticModel record);
}