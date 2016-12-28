package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.CreativeDataHourViewModel;
import com.pxene.pap.domain.model.basic.view.CreativeDataHourViewModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeDataHourViewDao {
    long countByExample(CreativeDataHourViewModelExample example);

    int deleteByExample(CreativeDataHourViewModelExample example);

    int insert(CreativeDataHourViewModel record);

    int insertSelective(CreativeDataHourViewModel record);

    List<CreativeDataHourViewModel> selectByExample(CreativeDataHourViewModelExample example);

    int updateByExampleSelective(@Param("record") CreativeDataHourViewModel record, @Param("example") CreativeDataHourViewModelExample example);

    int updateByExample(@Param("record") CreativeDataHourViewModel record, @Param("example") CreativeDataHourViewModelExample example);
}