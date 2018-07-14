package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RuleGroupModel;
import com.pxene.pap.domain.models.RuleGroupModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleGroupDao {
    long countByExample(RuleGroupModelExample example);

    int deleteByExample(RuleGroupModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RuleGroupModel record);

    int insertSelective(RuleGroupModel record);

    List<RuleGroupModel> selectByExample(RuleGroupModelExample example);

    RuleGroupModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RuleGroupModel record, @Param("example") RuleGroupModelExample example);

    int updateByExample(@Param("record") RuleGroupModel record, @Param("example") RuleGroupModelExample example);

    int updateByPrimaryKeySelective(RuleGroupModel record);

    int updateByPrimaryKey(RuleGroupModel record);
}