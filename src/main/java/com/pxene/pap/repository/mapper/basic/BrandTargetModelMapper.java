package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.BrandTargetModel;
import com.pxene.pap.domain.model.basic.BrandTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface BrandTargetModelMapper {
    int countByExample(BrandTargetModelExample example);

    int deleteByExample(BrandTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(BrandTargetModel record);

    int insertSelective(BrandTargetModel record);

    List<BrandTargetModel> selectByExample(BrandTargetModelExample example);

    BrandTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BrandTargetModel record, @Param("example") BrandTargetModelExample example);

    int updateByExample(@Param("record") BrandTargetModel record, @Param("example") BrandTargetModelExample example);

    int updateByPrimaryKeySelective(BrandTargetModel record);

    int updateByPrimaryKey(BrandTargetModel record);
}