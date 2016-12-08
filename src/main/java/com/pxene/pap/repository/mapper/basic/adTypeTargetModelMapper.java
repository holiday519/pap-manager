package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.adTypeTargetModel;
import com.pxene.pap.domain.model.basic.adTypeTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface adTypeTargetModelMapper {
    long countByExample(adTypeTargetModelExample example);

    int deleteByExample(adTypeTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(adTypeTargetModel record);

    int insertSelective(adTypeTargetModel record);

    List<adTypeTargetModel> selectByExample(adTypeTargetModelExample example);

    adTypeTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") adTypeTargetModel record, @Param("example") adTypeTargetModelExample example);

    int updateByExample(@Param("record") adTypeTargetModel record, @Param("example") adTypeTargetModelExample example);

    int updateByPrimaryKeySelective(adTypeTargetModel record);

    int updateByPrimaryKey(adTypeTargetModel record);
}