package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.ImageTypeModel;
import com.pxene.pap.domain.models.ImageTypeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ImageTypeDao {
    long countByExample(ImageTypeModelExample example);

    int deleteByExample(ImageTypeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageTypeModel record);

    int insertSelective(ImageTypeModel record);

    List<ImageTypeModel> selectByExample(ImageTypeModelExample example);

    ImageTypeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageTypeModel record, @Param("example") ImageTypeModelExample example);

    int updateByExample(@Param("record") ImageTypeModel record, @Param("example") ImageTypeModelExample example);

    int updateByPrimaryKeySelective(ImageTypeModel record);

    int updateByPrimaryKey(ImageTypeModel record);
}