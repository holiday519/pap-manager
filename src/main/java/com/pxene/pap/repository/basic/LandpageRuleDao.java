package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.LandpageRuleModel;
import com.pxene.pap.domain.models.LandpageRuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandpageRuleDao {
    long countByExample(LandpageRuleModelExample example);

    int deleteByExample(LandpageRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandpageRuleModel record);

    int insertSelective(LandpageRuleModel record);

    List<LandpageRuleModel> selectByExample(LandpageRuleModelExample example);

    LandpageRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandpageRuleModel record, @Param("example") LandpageRuleModelExample example);

    int updateByExample(@Param("record") LandpageRuleModel record, @Param("example") LandpageRuleModelExample example);

    int updateByPrimaryKeySelective(LandpageRuleModel record);

    int updateByPrimaryKey(LandpageRuleModel record);
}