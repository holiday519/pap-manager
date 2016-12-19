package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.VideoTypeModel;
import com.pxene.pap.domain.model.basic.VideoTypeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoTypeDao {
    long countByExample(VideoTypeModelExample example);

    int deleteByExample(VideoTypeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(VideoTypeModel record);

    int insertSelective(VideoTypeModel record);

    List<VideoTypeModel> selectByExample(VideoTypeModelExample example);

    VideoTypeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") VideoTypeModel record, @Param("example") VideoTypeModelExample example);

    int updateByExample(@Param("record") VideoTypeModel record, @Param("example") VideoTypeModelExample example);

    int updateByPrimaryKeySelective(VideoTypeModel record);

    int updateByPrimaryKey(VideoTypeModel record);
}