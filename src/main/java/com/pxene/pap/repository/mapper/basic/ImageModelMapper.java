package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.ImageModel;
import com.pxene.pap.domain.model.basic.ImageModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageModelMapper {
    long countByExample(ImageModelExample example);

    int deleteByExample(ImageModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageModel record);

    int insertSelective(ImageModel record);

    List<ImageModel> selectByExample(ImageModelExample example);

    ImageModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageModel record, @Param("example") ImageModelExample example);

    int updateByExample(@Param("record") ImageModel record, @Param("example") ImageModelExample example);

    int updateByPrimaryKeySelective(ImageModel record);

    int updateByPrimaryKey(ImageModel record);
}