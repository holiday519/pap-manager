package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.TimeModel;
import com.pxene.pap.domain.models.TimeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TimeDao {
    long countByExample(TimeModelExample example);

    int deleteByExample(TimeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(TimeModel record);

    int insertSelective(TimeModel record);

    List<TimeModel> selectByExample(TimeModelExample example);

    TimeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TimeModel record, @Param("example") TimeModelExample example);

    int updateByExample(@Param("record") TimeModel record, @Param("example") TimeModelExample example);

    int updateByPrimaryKeySelective(TimeModel record);

    int updateByPrimaryKey(TimeModel record);
}