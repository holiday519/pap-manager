package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.CreativeDataDayModel;
import com.pxene.pap.domain.model.CreativeDataDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CreativeDataDayDao {
    long countByExample(CreativeDataDayModelExample example);

    int deleteByExample(CreativeDataDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CreativeDataDayModel record);

    int insertSelective(CreativeDataDayModel record);

    List<CreativeDataDayModel> selectByExample(CreativeDataDayModelExample example);

    CreativeDataDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CreativeDataDayModel record, @Param("example") CreativeDataDayModelExample example);

    int updateByExample(@Param("record") CreativeDataDayModel record, @Param("example") CreativeDataDayModelExample example);

    int updateByPrimaryKeySelective(CreativeDataDayModel record);

    int updateByPrimaryKey(CreativeDataDayModel record);
}