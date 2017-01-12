package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RuleConditionModel;
import com.pxene.pap.domain.models.RuleConditionModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleConditionDao {
    long countByExample(RuleConditionModelExample example);

    int deleteByExample(RuleConditionModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RuleConditionModel record);

    int insertSelective(RuleConditionModel record);

    List<RuleConditionModel> selectByExample(RuleConditionModelExample example);

    RuleConditionModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RuleConditionModel record, @Param("example") RuleConditionModelExample example);

    int updateByExample(@Param("record") RuleConditionModel record, @Param("example") RuleConditionModelExample example);

    int updateByPrimaryKeySelective(RuleConditionModel record);

    int updateByPrimaryKey(RuleConditionModel record);
}