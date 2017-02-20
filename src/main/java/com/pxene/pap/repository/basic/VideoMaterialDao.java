package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.VideoMaterialModel;
import com.pxene.pap.domain.models.VideoMaterialModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoMaterialDao {
    long countByExample(VideoMaterialModelExample example);

    int deleteByExample(VideoMaterialModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoMaterialModel record);

    int insertSelective(VideoMaterialModel record);

    List<VideoMaterialModel> selectByExample(VideoMaterialModelExample example);

    VideoMaterialModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoMaterialModel record, @Param("example") VideoMaterialModelExample example);

    int updateByExample(@Param("record") VideoMaterialModel record, @Param("example") VideoMaterialModelExample example);

    int updateByPrimaryKeySelective(VideoMaterialModel record);

    int updateByPrimaryKey(VideoMaterialModel record);
}