package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.SizeTypeModel;
import com.pxene.pap.domain.model.basic.SizeTypeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SizeTypeModelMapper {
    int countByExample(SizeTypeModelExample example);

    int deleteByExample(SizeTypeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(SizeTypeModel record);

    int insertSelective(SizeTypeModel record);

    List<SizeTypeModel> selectByExample(SizeTypeModelExample example);

    SizeTypeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SizeTypeModel record, @Param("example") SizeTypeModelExample example);

    int updateByExample(@Param("record") SizeTypeModel record, @Param("example") SizeTypeModelExample example);

    int updateByPrimaryKeySelective(SizeTypeModel record);

    int updateByPrimaryKey(SizeTypeModel record);
}