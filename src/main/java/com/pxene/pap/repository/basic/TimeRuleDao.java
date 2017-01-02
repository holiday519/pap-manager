package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.TimeRuleModel;
import com.pxene.pap.domain.model.basic.TimeRuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TimeRuleDao {
    long countByExample(TimeRuleModelExample example);

    int deleteByExample(TimeRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(TimeRuleModel record);

    int insertSelective(TimeRuleModel record);

    List<TimeRuleModel> selectByExample(TimeRuleModelExample example);

    TimeRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TimeRuleModel record, @Param("example") TimeRuleModelExample example);

    int updateByExample(@Param("record") TimeRuleModel record, @Param("example") TimeRuleModelExample example);

    int updateByPrimaryKeySelective(TimeRuleModel record);

    int updateByPrimaryKey(TimeRuleModel record);
}