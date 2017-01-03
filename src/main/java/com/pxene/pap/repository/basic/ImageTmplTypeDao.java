package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.ImageTmplTypeModel;
import com.pxene.pap.domain.model.ImageTmplTypeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ImageTmplTypeDao {
    long countByExample(ImageTmplTypeModelExample example);

    int deleteByExample(ImageTmplTypeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageTmplTypeModel record);

    int insertSelective(ImageTmplTypeModel record);

    List<ImageTmplTypeModel> selectByExample(ImageTmplTypeModelExample example);

    ImageTmplTypeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageTmplTypeModel record, @Param("example") ImageTmplTypeModelExample example);

    int updateByExample(@Param("record") ImageTmplTypeModel record, @Param("example") ImageTmplTypeModelExample example);

    int updateByPrimaryKeySelective(ImageTmplTypeModel record);

    int updateByPrimaryKey(ImageTmplTypeModel record);
}