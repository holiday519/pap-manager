package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.CreativeRuleModel;
import com.pxene.pap.domain.models.CreativeRuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeRuleDao {
    long countByExample(CreativeRuleModelExample example);

    int deleteByExample(CreativeRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreativeRuleModel record);

    int insertSelective(CreativeRuleModel record);

    List<CreativeRuleModel> selectByExample(CreativeRuleModelExample example);

    CreativeRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreativeRuleModel record, @Param("example") CreativeRuleModelExample example);

    int updateByExample(@Param("record") CreativeRuleModel record, @Param("example") CreativeRuleModelExample example);

    int updateByPrimaryKeySelective(CreativeRuleModel record);

    int updateByPrimaryKey(CreativeRuleModel record);
}