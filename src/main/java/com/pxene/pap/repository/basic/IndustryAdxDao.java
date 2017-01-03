package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.IndustryAdxModel;
import com.pxene.pap.domain.model.IndustryAdxModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IndustryAdxDao {
    long countByExample(IndustryAdxModelExample example);

    int deleteByExample(IndustryAdxModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IndustryAdxModel record);

    int insertSelective(IndustryAdxModel record);

    List<IndustryAdxModel> selectByExample(IndustryAdxModelExample example);

    IndustryAdxModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IndustryAdxModel record, @Param("example") IndustryAdxModelExample example);

    int updateByExample(@Param("record") IndustryAdxModel record, @Param("example") IndustryAdxModelExample example);

    int updateByPrimaryKeySelective(IndustryAdxModel record);

    int updateByPrimaryKey(IndustryAdxModel record);
}