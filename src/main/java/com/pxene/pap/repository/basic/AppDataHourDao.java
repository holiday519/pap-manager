package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.AppDataHourModel;
import com.pxene.pap.domain.model.AppDataHourModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AppDataHourDao {
    long countByExample(AppDataHourModelExample example);

    int deleteByExample(AppDataHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppDataHourModel record);

    int insertSelective(AppDataHourModel record);

    List<AppDataHourModel> selectByExample(AppDataHourModelExample example);

    AppDataHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppDataHourModel record, @Param("example") AppDataHourModelExample example);

    int updateByExample(@Param("record") AppDataHourModel record, @Param("example") AppDataHourModelExample example);

    int updateByPrimaryKeySelective(AppDataHourModel record);

    int updateByPrimaryKey(AppDataHourModel record);
}