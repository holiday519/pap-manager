package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.CampaignTargetModel;
import com.pxene.pap.domain.models.CampaignTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignTargetDao {
    long countByExample(CampaignTargetModelExample example);

    int deleteByExample(CampaignTargetModelExample example);

    int insert(CampaignTargetModel record);

    int insertSelective(CampaignTargetModel record);

    List<CampaignTargetModel> selectByExampleWithBLOBs(CampaignTargetModelExample example);

    List<CampaignTargetModel> selectByExample(CampaignTargetModelExample example);

    int updateByExampleSelective(@Param("record") CampaignTargetModel record, @Param("example") CampaignTargetModelExample example);

    int updateByExampleWithBLOBs(@Param("record") CampaignTargetModel record, @Param("example") CampaignTargetModelExample example);

    int updateByExample(@Param("record") CampaignTargetModel record, @Param("example") CampaignTargetModelExample example);
}