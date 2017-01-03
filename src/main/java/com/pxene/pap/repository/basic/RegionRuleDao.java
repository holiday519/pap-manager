package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RegionRuleModel;
import com.pxene.pap.domain.models.RegionRuleModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RegionRuleDao {
    long countByExample(RegionRuleModelExample example);

    int deleteByExample(RegionRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RegionRuleModel record);

    int insertSelective(RegionRuleModel record);

    List<RegionRuleModel> selectByExample(RegionRuleModelExample example);

    RegionRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RegionRuleModel record, @Param("example") RegionRuleModelExample example);

    int updateByExample(@Param("record") RegionRuleModel record, @Param("example") RegionRuleModelExample example);

    int updateByPrimaryKeySelective(RegionRuleModel record);

    int updateByPrimaryKey(RegionRuleModel record);
}