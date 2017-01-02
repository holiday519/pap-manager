package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.LandPageRuleModel;
import com.pxene.pap.domain.model.basic.LandPageRuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandPageRuleDao {
    long countByExample(LandPageRuleModelExample example);

    int deleteByExample(LandPageRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandPageRuleModel record);

    int insertSelective(LandPageRuleModel record);

    List<LandPageRuleModel> selectByExample(LandPageRuleModelExample example);

    LandPageRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandPageRuleModel record, @Param("example") LandPageRuleModelExample example);

    int updateByExample(@Param("record") LandPageRuleModel record, @Param("example") LandPageRuleModelExample example);

    int updateByPrimaryKeySelective(LandPageRuleModel record);

    int updateByPrimaryKey(LandPageRuleModel record);
}