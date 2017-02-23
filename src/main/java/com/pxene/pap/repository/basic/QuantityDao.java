package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuantityDao {
    long countByExample(QuantityModelExample example);

    int deleteByExample(QuantityModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(QuantityModel record);

    int insertSelective(QuantityModel record);

    List<QuantityModel> selectByExample(QuantityModelExample example);

    QuantityModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") QuantityModel record, @Param("example") QuantityModelExample example);

    int updateByExample(@Param("record") QuantityModel record, @Param("example") QuantityModelExample example);

    int updateByPrimaryKeySelective(QuantityModel record);

    int updateByPrimaryKey(QuantityModel record);
}