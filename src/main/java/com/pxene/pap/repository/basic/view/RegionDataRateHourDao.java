package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.RegionDataRateHourModel;
import com.pxene.pap.domain.model.basic.view.RegionDataRateHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionDataRateHourDao {
    long countByExample(RegionDataRateHourModelExample example);

    int deleteByExample(RegionDataRateHourModelExample example);

    int insert(RegionDataRateHourModel record);

    int insertSelective(RegionDataRateHourModel record);

    List<RegionDataRateHourModel> selectByExample(RegionDataRateHourModelExample example);

    int updateByExampleSelective(@Param("record") RegionDataRateHourModel record, @Param("example") RegionDataRateHourModelExample example);

    int updateByExample(@Param("record") RegionDataRateHourModel record, @Param("example") RegionDataRateHourModelExample example);
}