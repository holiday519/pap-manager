package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.MonitorModel;
import com.pxene.pap.domain.models.MonitorModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MonitorDao {
    long countByExample(MonitorModelExample example);

    int deleteByExample(MonitorModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(MonitorModel record);

    int insertSelective(MonitorModel record);

    List<MonitorModel> selectByExample(MonitorModelExample example);

    MonitorModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MonitorModel record, @Param("example") MonitorModelExample example);

    int updateByExample(@Param("record") MonitorModel record, @Param("example") MonitorModelExample example);

    int updateByPrimaryKeySelective(MonitorModel record);

    int updateByPrimaryKey(MonitorModel record);
}