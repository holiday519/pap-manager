package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.VideoTmplModel;
import com.pxene.pap.domain.model.basic.VideoTmplModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface VideoTmplModelMapper {
    int countByExample(VideoTmplModelExample example);

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