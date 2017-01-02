package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.AppDataRateHourModel;
import com.pxene.pap.domain.model.basic.view.AppDataRateHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppDataRateHourDao {
    long countByExample(AppDataRateHourModelExample example);

    int deleteByExample(AppDataRateHourModelExample example);

    int insert(AppDataRateHourModel record);

    int insertSelective(AppDataRateHourModel record);

    List<AppDataRateHourModel> selectByExample(AppDataRateHourModelExample example);

    int updateByExampleSelective(@Param("record") AppDataRateHourModel record, @Param("example") AppDataRateHourModelExample example);

    int updateByExample(@Param("record") AppDataRateHourModel record, @Param("example") AppDataRateHourModelExample example);
}