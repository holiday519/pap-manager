package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.RegionFlowHourModel;
import com.pxene.pap.domain.model.basic.RegionFlowHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionFlowHourDao {
    long countByExample(RegionFlowHourModelExample example);

    int deleteByExample(RegionFlowHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionFlowHourModel record);

    int insertSelective(RegionFlowHourModel record);

    List<RegionFlowHourModel> selectByExample(RegionFlowHourModelExample example);

    RegionFlowHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RegionFlowHourModel record, @Param("example") RegionFlowHourModelExample example);

    int updateByExample(@Param("record") RegionFlowHourModel record, @Param("example") RegionFlowHourModelExample example);

    int updateByPrimaryKeySelective(RegionFlowHourModel record);

    int updateByPrimaryKey(RegionFlowHourModel record);
}