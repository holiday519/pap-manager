package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.ProjectKpiModel;
import com.pxene.pap.domain.model.basic.ProjectKpiModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectKpiModelMapper {
    long countByExample(ProjectKpiModelExample example);

    int deleteByExample(ProjectKpiModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProjectKpiModel record);

    int insertSelective(ProjectKpiModel record);

    List<ProjectKpiModel> selectByExample(ProjectKpiModelExample example);

    ProjectKpiModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProjectKpiModel record, @Param("example") ProjectKpiModelExample example);

    int updateByExample(@Param("record") ProjectKpiModel record, @Param("example") ProjectKpiModelExample example);

    int updateByPrimaryKeySelective(ProjectKpiModel record);

    int updateByPrimaryKey(ProjectKpiModel record);
}