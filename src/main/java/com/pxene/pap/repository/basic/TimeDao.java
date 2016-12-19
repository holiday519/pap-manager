package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.TimeModel;
import com.pxene.pap.domain.model.basic.TimeModelExample;
import com.pxene.pap.domain.model.basic.TimeModelWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TimeDao {
    long countByExample(TimeModelExample example);

    int deleteByExample(TimeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(TimeModelWithBLOBs record);

    int insertSelective(TimeModelWithBLOBs record);

    List<TimeModelWithBLOBs> selectByExampleWithBLOBs(TimeModelExample example);

    List<TimeModel> selectByExample(TimeModelExample example);

    TimeModelWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TimeModelWithBLOBs record, @Param("example") TimeModelExample example);

    int updateByExampleWithBLOBs(@Param("record") TimeModelWithBLOBs record, @Param("example") TimeModelExample example);

    int updateByExample(@Param("record") TimeModel record, @Param("example") TimeModelExample example);

    int updateByPrimaryKeySelective(TimeModelWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TimeModelWithBLOBs record);

    int updateByPrimaryKey(TimeModel record);
}