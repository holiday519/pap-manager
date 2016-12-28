package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.RegionDataHourViewModel;
import com.pxene.pap.domain.model.basic.view.RegionDataHourViewModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionDataHourViewDao {
    long countByExample(RegionDataHourViewModelExample example);

    int deleteByExample(RegionDataHourViewModelExample example);

    int insert(RegionDataHourViewModel record);

    int insertSelective(RegionDataHourViewModel record);

    List<RegionDataHourViewModel> selectByExample(RegionDataHourViewModelExample example);

    int updateByExampleSelective(@Param("record") RegionDataHourViewModel record, @Param("example") RegionDataHourViewModelExample example);

    int updateByExample(@Param("record") RegionDataHourViewModel record, @Param("example") RegionDataHourViewModelExample example);
}