package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.CampaignCostModel;
import com.pxene.pap.domain.model.basic.CampaignCostModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignCostDao {
    long countByExample(CampaignCostModelExample example);

    int deleteByExample(CampaignCostModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CampaignCostModel record);

    int insertSelective(CampaignCostModel record);

    List<CampaignCostModel> selectByExample(CampaignCostModelExample example);

    CampaignCostModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CampaignCostModel record, @Param("example") CampaignCostModelExample example);

    int updateByExample(@Param("record") CampaignCostModel record, @Param("example") CampaignCostModelExample example);

    int updateByPrimaryKeySelective(CampaignCostModel record);

    int updateByPrimaryKey(CampaignCostModel record);
}