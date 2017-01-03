package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.CampaignRuleModel;
import com.pxene.pap.domain.model.CampaignRuleModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CampaignRuleDao {
    long countByExample(CampaignRuleModelExample example);

    int deleteByExample(CampaignRuleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CampaignRuleModel record);

    int insertSelective(CampaignRuleModel record);

    List<CampaignRuleModel> selectByExample(CampaignRuleModelExample example);

    CampaignRuleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CampaignRuleModel record, @Param("example") CampaignRuleModelExample example);

    int updateByExample(@Param("record") CampaignRuleModel record, @Param("example") CampaignRuleModelExample example);

    int updateByPrimaryKeySelective(CampaignRuleModel record);

    int updateByPrimaryKey(CampaignRuleModel record);
}