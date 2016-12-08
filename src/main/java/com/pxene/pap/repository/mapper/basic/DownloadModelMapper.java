package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.DownloadModel;
import com.pxene.pap.domain.model.basic.DownloadModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DownloadModelMapper {
    int countByExample(DownloadModelExample example);

    int deleteByExample(DownloadModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(DownloadModel record);

    int insertSelective(DownloadModel record);

    List<DownloadModel> selectByExample(DownloadModelExample example);

    DownloadModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DownloadModel record, @Param("example") DownloadModelExample example);

    int updateByExample(@Param("record") DownloadModel record, @Param("example") DownloadModelExample example);

    int updateByPrimaryKeySelective(DownloadModel record);

    int updateByPrimaryKey(DownloadModel record);
}