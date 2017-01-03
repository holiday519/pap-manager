package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.DeviceTargetModel;
import com.pxene.pap.domain.models.DeviceTargetModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DeviceTargetDao {
    long countByExample(DeviceTargetModelExample example);

    int deleteByExample(DeviceTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(DeviceTargetModel record);

    int insertSelective(DeviceTargetModel record);

    List<DeviceTargetModel> selectByExample(DeviceTargetModelExample example);

    DeviceTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DeviceTargetModel record, @Param("example") DeviceTargetModelExample example);

    int updateByExample(@Param("record") DeviceTargetModel record, @Param("example") DeviceTargetModelExample example);

    int updateByPrimaryKeySelective(DeviceTargetModel record);

    int updateByPrimaryKey(DeviceTargetModel record);
}