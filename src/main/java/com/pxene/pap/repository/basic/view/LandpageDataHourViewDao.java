package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.LandpageDataHourViewModel;
import com.pxene.pap.domain.model.basic.view.LandpageDataHourViewModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandpageDataHourViewDao {
    long countByExample(LandpageDataHourViewModelExample example);

    int deleteByExample(LandpageDataHourViewModelExample example);

    int insert(LandpageDataHourViewModel record);

    int insertSelective(LandpageDataHourViewModel record);

    List<LandpageDataHourViewModel> selectByExample(LandpageDataHourViewModelExample example);

    int updateByExampleSelective(@Param("record") LandpageDataHourViewModel record, @Param("example") LandpageDataHourViewModelExample example);

    int updateByExample(@Param("record") LandpageDataHourViewModel record, @Param("example") LandpageDataHourViewModelExample example);
}