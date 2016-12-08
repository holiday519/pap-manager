package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.domain.model.basic.VideoModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoModelMapper {
    int countByExample(VideoModelExample example);

    int deleteByExample(VideoModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoModel record);

    int insertSelective(VideoModel record);

    List<VideoModel> selectByExample(VideoModelExample example);

    VideoModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoModel record, @Param("example") VideoModelExample example);

    int updateByExample(@Param("record") VideoModel record, @Param("example") VideoModelExample example);

    int updateByPrimaryKeySelective(VideoModel record);

    int updateByPrimaryKey(VideoModel record);
}