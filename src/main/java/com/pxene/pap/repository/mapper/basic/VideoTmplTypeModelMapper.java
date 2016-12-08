package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.VideoTmplTypeModel;
import com.pxene.pap.domain.model.basic.VideoTmplTypeModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoTmplTypeModelMapper {
    int countByExample(VideoTmplTypeModelExample example);

    int deleteByExample(VideoTmplTypeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoTmplTypeModel record);

    int insertSelective(VideoTmplTypeModel record);

    List<VideoTmplTypeModel> selectByExample(VideoTmplTypeModelExample example);

    VideoTmplTypeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoTmplTypeModel record, @Param("example") VideoTmplTypeModelExample example);

    int updateByExample(@Param("record") VideoTmplTypeModel record, @Param("example") VideoTmplTypeModelExample example);

    int updateByPrimaryKeySelective(VideoTmplTypeModel record);

    int updateByPrimaryKey(VideoTmplTypeModel record);
}