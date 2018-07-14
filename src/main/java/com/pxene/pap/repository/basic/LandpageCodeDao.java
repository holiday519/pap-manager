package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.LandpageCodeModel;
import com.pxene.pap.domain.models.LandpageCodeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandpageCodeDao {
    long countByExample(LandpageCodeModelExample example);

    int deleteByExample(LandpageCodeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandpageCodeModel record);

    int insertSelective(LandpageCodeModel record);

    List<LandpageCodeModel> selectByExample(LandpageCodeModelExample example);

    LandpageCodeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandpageCodeModel record, @Param("example") LandpageCodeModelExample example);

    int updateByExample(@Param("record") LandpageCodeModel record, @Param("example") LandpageCodeModelExample example);

    int updateByPrimaryKeySelective(LandpageCodeModel record);

    int updateByPrimaryKey(LandpageCodeModel record);
}