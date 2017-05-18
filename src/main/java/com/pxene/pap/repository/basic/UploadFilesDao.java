package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.UploadFilesModel;
import com.pxene.pap.domain.models.UploadFilesModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UploadFilesDao {
    long countByExample(UploadFilesModelExample example);

    int deleteByExample(UploadFilesModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(UploadFilesModel record);

    int insertSelective(UploadFilesModel record);

    List<UploadFilesModel> selectByExample(UploadFilesModelExample example);

    UploadFilesModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UploadFilesModel record, @Param("example") UploadFilesModelExample example);

    int updateByExample(@Param("record") UploadFilesModel record, @Param("example") UploadFilesModelExample example);

    int updateByPrimaryKeySelective(UploadFilesModel record);

    int updateByPrimaryKey(UploadFilesModel record);
}