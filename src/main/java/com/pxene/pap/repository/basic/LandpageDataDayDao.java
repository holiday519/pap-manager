package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.LandpageDataDayModel;
import com.pxene.pap.domain.model.LandpageDataDayModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LandpageDataDayDao {
    long countByExample(LandpageDataDayModelExample example);

    int deleteByExample(LandpageDataDayModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LandpageDataDayModel record);

    int insertSelective(LandpageDataDayModel record);

    List<LandpageDataDayModel> selectByExample(LandpageDataDayModelExample example);

    LandpageDataDayModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LandpageDataDayModel record, @Param("example") LandpageDataDayModelExample example);

    int updateByExample(@Param("record") LandpageDataDayModel record, @Param("example") LandpageDataDayModelExample example);

    int updateByPrimaryKeySelective(LandpageDataDayModel record);

    int updateByPrimaryKey(LandpageDataDayModel record);
}