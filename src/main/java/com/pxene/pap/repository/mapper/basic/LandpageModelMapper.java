package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.LandpageModel;
import com.pxene.pap.domain.model.basic.LandpageModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface LandpageModelMapper {
    int countByExample(LandpageModelExample example);

    int deleteByExample(LandpageModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandpageModel record);

    int insertSelective(LandpageModel record);

    List<LandpageModel> selectByExample(LandpageModelExample example);

    LandpageModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandpageModel record, @Param("example") LandpageModelExample example);

    int updateByExample(@Param("record") LandpageModel record, @Param("example") LandpageModelExample example);

    int updateByPrimaryKeySelective(LandpageModel record);

    int updateByPrimaryKey(LandpageModel record);
}