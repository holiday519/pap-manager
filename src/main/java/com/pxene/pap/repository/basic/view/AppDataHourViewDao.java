package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.AppDataHourViewModel;
import com.pxene.pap.domain.model.basic.view.AppDataHourViewModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppDataHourViewDao {
    long countByExample(AppDataHourViewModelExample example);

    int deleteByExample(AppDataHourViewModelExample example);

    int insert(AppDataHourViewModel record);

    int insertSelective(AppDataHourViewModel record);

    List<AppDataHourViewModel> selectByExample(AppDataHourViewModelExample example);

    int updateByExampleSelective(@Param("record") AppDataHourViewModel record, @Param("example") AppDataHourViewModelExample example);

    int updateByExample(@Param("record") AppDataHourViewModel record, @Param("example") AppDataHourViewModelExample example);
}