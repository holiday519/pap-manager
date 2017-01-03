package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.VideoTmplModel;
import com.pxene.pap.domain.model.VideoTmplModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface VideoTmplDao {
    long countByExample(VideoTmplModelExample example);

    int deleteByExample(VideoTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoTmplModel record);

    int insertSelective(VideoTmplModel record);

    List<VideoTmplModel> selectByExample(VideoTmplModelExample example);

    VideoTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoTmplModel record, @Param("example") VideoTmplModelExample example);

    int updateByExample(@Param("record") VideoTmplModel record, @Param("example") VideoTmplModelExample example);

    int updateByPrimaryKeySelective(VideoTmplModel record);

    int updateByPrimaryKey(VideoTmplModel record);
}