package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.CampaignKpiModel;
import com.pxene.pap.domain.models.view.CampaignKpiModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignKpiDao {
    long countByExample(CampaignKpiModelExample example);

    int deleteByExample(CampaignKpiModelExample example);

    int insert(CampaignKpiModel record);

    int insertSelective(CampaignKpiModel record);

    List<CampaignKpiModel> selectByExample(CampaignKpiModelExample example);

    int updateByExampleSelective(@Param("record") CampaignKpiModel record, @Param("example") CampaignKpiModelExample example);

    int updateByExample(@Param("record") CampaignKpiModel record, @Param("example") CampaignKpiModelExample example);
}