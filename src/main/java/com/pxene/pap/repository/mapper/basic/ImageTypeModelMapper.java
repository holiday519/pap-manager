package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.ImageTypeModel;
import com.pxene.pap.domain.model.basic.ImageTypeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageTypeModelMapper {
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