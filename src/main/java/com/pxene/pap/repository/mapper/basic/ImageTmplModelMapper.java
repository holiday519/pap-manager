package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.ImageTmplModel;
import com.pxene.pap.domain.model.basic.ImageTmplModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageTmplModelMapper {
    int countByExample(ImageTmplModelExample example);

    int deleteByExample(ImageTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageTmplModel record);

    int insertSelective(ImageTmplModel record);

    List<ImageTmplModel> selectByExample(ImageTmplModelExample example);

    ImageTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageTmplModel record, @Param("example") ImageTmplModelExample example);

    int updateByExample(@Param("record") ImageTmplModel record, @Param("example") ImageTmplModelExample example);

    int updateByPrimaryKeySelective(ImageTmplModel record);

    int updateByPrimaryKey(ImageTmplModel record);
}