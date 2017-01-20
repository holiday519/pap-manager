package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RuleLogModel;
import com.pxene.pap.domain.models.RuleLogModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleLogDao {
    long countByExample(RuleLogModelExample example);

    int deleteByExample(RuleLogModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RuleLogModel record);

    int insertSelective(RuleLogModel record);

    List<RuleLogModel> selectByExample(RuleLogModelExample example);

    RuleLogModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RuleLogModel record, @Param("example") RuleLogModelExample example);

    int updateByExample(@Param("record") RuleLogModel record, @Param("example") RuleLogModelExample example);

    int updateByPrimaryKeySelective(RuleLogModel record);

    int updateByPrimaryKey(RuleLogModel record);
}