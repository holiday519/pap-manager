package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdxDao {
    long countByExample(AdxModelExample example);

    int deleteByExample(AdxModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdxModel record);

    int insertSelective(AdxModel record);

    List<AdxModel> selectByExample(AdxModelExample example);

    AdxModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdxModel record, @Param("example") AdxModelExample example);

    int updateByExample(@Param("record") AdxModel record, @Param("example") AdxModelExample example);

    int updateByPrimaryKeySelective(AdxModel record);

    int updateByPrimaryKey(AdxModel record);
}