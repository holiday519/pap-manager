package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignDao {
    long countByExample(CampaignModelExample example);

    int deleteByExample(CampaignModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CampaignModel record);

    int insertSelective(CampaignModel record);

    List<CampaignModel> selectByExample(CampaignModelExample example);

    CampaignModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CampaignModel record, @Param("example") CampaignModelExample example);

    int updateByExample(@Param("record") CampaignModel record, @Param("example") CampaignModelExample example);

    int updateByPrimaryKeySelective(CampaignModel record);

    int updateByPrimaryKey(CampaignModel record);
}