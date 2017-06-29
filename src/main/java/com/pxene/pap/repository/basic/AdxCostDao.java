package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdxCostModel;
import com.pxene.pap.domain.models.AdxCostModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdxCostDao {
    long countByExample(AdxCostModelExample example);

    int deleteByExample(AdxCostModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdxCostModel record);

    int insertSelective(AdxCostModel record);

    List<AdxCostModel> selectByExample(AdxCostModelExample example);

    AdxCostModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdxCostModel record, @Param("example") AdxCostModelExample example);

    int updateByExample(@Param("record") AdxCostModel record, @Param("example") AdxCostModelExample example);

    int updateByPrimaryKeySelective(AdxCostModel record);

    int updateByPrimaryKey(AdxCostModel record);
}