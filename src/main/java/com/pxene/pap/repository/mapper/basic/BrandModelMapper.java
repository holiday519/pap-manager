package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.BrandModel;
import com.pxene.pap.domain.model.basic.BrandModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface BrandModelMapper {
    int countByExample(BrandModelExample example);

    int deleteByExample(BrandModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(BrandModel record);

    int insertSelective(BrandModel record);

    List<BrandModel> selectByExample(BrandModelExample example);

    BrandModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BrandModel record, @Param("example") BrandModelExample example);

    int updateByExample(@Param("record") BrandModel record, @Param("example") BrandModelExample example);

    int updateByPrimaryKeySelective(BrandModel record);

    int updateByPrimaryKey(BrandModel record);
}