package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.ProjectModel;
import com.pxene.pap.domain.model.ProjectModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProjectDao {
    long countByExample(ProjectModelExample example);

    int deleteByExample(ProjectModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProjectModel record);

    int insertSelective(ProjectModel record);

    List<ProjectModel> selectByExample(ProjectModelExample example);

    ProjectModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProjectModel record, @Param("example") ProjectModelExample example);

    int updateByExample(@Param("record") ProjectModel record, @Param("example") ProjectModelExample example);

    int updateByPrimaryKeySelective(ProjectModel record);

    int updateByPrimaryKey(ProjectModel record);
}