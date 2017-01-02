package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.LandpageDataRateHourModel;
import com.pxene.pap.domain.model.basic.view.LandpageDataRateHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandpageDataRateHourDao {
    long countByExample(LandpageDataRateHourModelExample example);

    int deleteByExample(LandpageDataRateHourModelExample example);

    int insert(LandpageDataRateHourModel record);

    int insertSelective(LandpageDataRateHourModel record);

    List<LandpageDataRateHourModel> selectByExample(LandpageDataRateHourModelExample example);

    int updateByExampleSelective(@Param("record") LandpageDataRateHourModel record, @Param("example") LandpageDataRateHourModelExample example);

    int updateByExample(@Param("record") LandpageDataRateHourModel record, @Param("example") LandpageDataRateHourModelExample example);
}