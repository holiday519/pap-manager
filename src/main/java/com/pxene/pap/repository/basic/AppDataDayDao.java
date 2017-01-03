package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.AppDataDayModel;
import com.pxene.pap.domain.model.AppDataDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppDataDayDao {
    long countByExample(AppDataDayModelExample example);

    int deleteByExample(AppDataDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppDataDayModel record);

    int insertSelective(AppDataDayModel record);

    List<AppDataDayModel> selectByExample(AppDataDayModelExample example);

    AppDataDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppDataDayModel record, @Param("example") AppDataDayModelExample example);

    int updateByExample(@Param("record") AppDataDayModel record, @Param("example") AppDataDayModelExample example);

    int updateByPrimaryKeySelective(AppDataDayModel record);

    int updateByPrimaryKey(AppDataDayModel record);
}