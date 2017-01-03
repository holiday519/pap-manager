package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.CampaignTmplPriceModel;
import com.pxene.pap.domain.model.basic.CampaignTmplPriceModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CampaignTmplPriceDao {
    long countByExample(CampaignTmplPriceModelExample example);

    int deleteByExample(CampaignTmplPriceModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CampaignTmplPriceModel record);

    int insertSelective(CampaignTmplPriceModel record);

    List<CampaignTmplPriceModel> selectByExample(CampaignTmplPriceModelExample example);

    CampaignTmplPriceModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CampaignTmplPriceModel record, @Param("example") CampaignTmplPriceModelExample example);

    int updateByExample(@Param("record") CampaignTmplPriceModel record, @Param("example") CampaignTmplPriceModelExample example);

    int updateByPrimaryKeySelective(CampaignTmplPriceModel record);

    int updateByPrimaryKey(CampaignTmplPriceModel record);
}