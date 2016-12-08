package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.DownLoadModel;
import com.pxene.pap.domain.model.basic.DownLoadModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DownLoadModelMapper {
    long countByExample(DownLoadModelExample example);

    int deleteByExample(DownLoadModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(DownLoadModel record);

    int insertSelective(DownLoadModel record);

    List<DownLoadModel> selectByExample(DownLoadModelExample example);

    DownLoadModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DownLoadModel record, @Param("example") DownLoadModelExample example);

    int updateByExample(@Param("record") DownLoadModel record, @Param("example") DownLoadModelExample example);

    int updateByPrimaryKeySelective(DownLoadModel record);

    int updateByPrimaryKey(DownLoadModel record);
}