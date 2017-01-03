package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.LandpageDataHourModel;
import com.pxene.pap.domain.models.LandpageDataHourModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LandpageDataHourDao {
    long countByExample(LandpageDataHourModelExample example);

    int deleteByExample(LandpageDataHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LandpageDataHourModel record);

    int insertSelective(LandpageDataHourModel record);

    List<LandpageDataHourModel> selectByExample(LandpageDataHourModelExample example);

    LandpageDataHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LandpageDataHourModel record, @Param("example") LandpageDataHourModelExample example);

    int updateByExample(@Param("record") LandpageDataHourModel record, @Param("example") LandpageDataHourModelExample example);

    int updateByPrimaryKeySelective(LandpageDataHourModel record);

    int updateByPrimaryKey(LandpageDataHourModel record);
}