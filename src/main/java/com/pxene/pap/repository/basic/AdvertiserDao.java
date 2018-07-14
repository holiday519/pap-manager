package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdvertiserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertiserDao {
    long countByExample(AdvertiserModelExample example);

    int deleteByExample(AdvertiserModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdvertiserModel record);

    int insertSelective(AdvertiserModel record);

    List<AdvertiserModel> selectByExample(AdvertiserModelExample example);

    AdvertiserModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdvertiserModel record, @Param("example") AdvertiserModelExample example);

    int updateByExample(@Param("record") AdvertiserModel record, @Param("example") AdvertiserModelExample example);

    int updateByPrimaryKeySelective(AdvertiserModel record);

    int updateByPrimaryKey(AdvertiserModel record);
}