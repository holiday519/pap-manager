package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RuleModel;
import com.pxene.pap.domain.models.RuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleDao {
    long countByExample(RuleModelExample example);

    int deleteByExample(RuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RuleModel record);

    int insertSelective(RuleModel record);

    List<RuleModel> selectByExample(RuleModelExample example);

    RuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RuleModel record, @Param("example") RuleModelExample example);

    int updateByExample(@Param("record") RuleModel record, @Param("example") RuleModelExample example);

    int updateByPrimaryKeySelective(RuleModel record);

    int updateByPrimaryKey(RuleModel record);
}