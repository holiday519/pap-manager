package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageMaterialModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageMaterialDao {
    long countByExample(ImageMaterialModelExample example);

    int deleteByExample(ImageMaterialModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageMaterialModel record);

    int insertSelective(ImageMaterialModel record);

    List<ImageMaterialModel> selectByExample(ImageMaterialModelExample example);

    ImageMaterialModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageMaterialModel record, @Param("example") ImageMaterialModelExample example);

    int updateByExample(@Param("record") ImageMaterialModel record, @Param("example") ImageMaterialModelExample example);

    int updateByPrimaryKeySelective(ImageMaterialModel record);

    int updateByPrimaryKey(ImageMaterialModel record);
}