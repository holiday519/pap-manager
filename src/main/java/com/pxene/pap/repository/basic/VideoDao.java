package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.VideoModel;
import com.pxene.pap.domain.model.VideoModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VideoDao {
    long countByExample(VideoModelExample example);

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