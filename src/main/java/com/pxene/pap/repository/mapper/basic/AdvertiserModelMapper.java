package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertiserModelMapper {
    int countByExample(AdvertiserModelExample example);

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