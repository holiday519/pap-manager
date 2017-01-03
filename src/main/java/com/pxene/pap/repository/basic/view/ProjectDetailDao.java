package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.ProjectDetailModel;
import com.pxene.pap.domain.models.view.ProjectDetailModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProjectDetailDao {
    long countByExample(ProjectDetailModelExample example);

    int deleteByExample(ProjectDetailModelExample example);

    int insert(ProjectDetailModel record);

    int insertSelective(ProjectDetailModel record);

    List<ProjectDetailModel> selectByExample(ProjectDetailModelExample example);

    int updateByExampleSelective(@Param("record") ProjectDetailModel record, @Param("example") ProjectDetailModelExample example);

    int updateByExample(@Param("record") ProjectDetailModel record, @Param("example") ProjectDetailModelExample example);
}