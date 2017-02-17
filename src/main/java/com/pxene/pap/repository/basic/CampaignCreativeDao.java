package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.CampaignCreativeModel;
import com.pxene.pap.domain.models.CampaignCreativeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignCreativeDao {
    long countByExample(CampaignCreativeModelExample example);

    int deleteByExample(CampaignCreativeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CampaignCreativeModel record);

    int insertSelective(CampaignCreativeModel record);

    List<CampaignCreativeModel> selectByExample(CampaignCreativeModelExample example);

    CampaignCreativeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CampaignCreativeModel record, @Param("example") CampaignCreativeModelExample example);

    int updateByExample(@Param("record") CampaignCreativeModel record, @Param("example") CampaignCreativeModelExample example);

    int updateByPrimaryKeySelective(CampaignCreativeModel record);

    int updateByPrimaryKey(CampaignCreativeModel record);
}