package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AppRuleModel;
import com.pxene.pap.domain.models.AppRuleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppRuleDao {
    long countByExample(AppRuleModelExample example);

    int deleteByExample(AppRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppRuleModel record);

    int insertSelective(AppRuleModel record);

    List<AppRuleModel> selectByExample(AppRuleModelExample example);

    AppRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppRuleModel record, @Param("example") AppRuleModelExample example);

    int updateByExample(@Param("record") AppRuleModel record, @Param("example") AppRuleModelExample example);

    int updateByPrimaryKeySelective(AppRuleModel record);

    int updateByPrimaryKey(AppRuleModel record);
}