package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.view.ImageSizeTypeModel;
import com.pxene.pap.domain.model.view.ImageSizeTypeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ImageSizeTypeDao {
    long countByExample(ImageSizeTypeModelExample example);

    int deleteByExample(ImageSizeTypeModelExample example);

    int insert(ImageSizeTypeModel record);

    int insertSelective(ImageSizeTypeModel record);

    List<ImageSizeTypeModel> selectByExample(ImageSizeTypeModelExample example);

    int updateByExampleSelective(@Param("record") ImageSizeTypeModel record, @Param("example") ImageSizeTypeModelExample example);

    int updateByExample(@Param("record") ImageSizeTypeModel record, @Param("example") ImageSizeTypeModelExample example);
}