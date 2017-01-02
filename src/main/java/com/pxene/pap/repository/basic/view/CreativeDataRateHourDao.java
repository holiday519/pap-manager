package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.model.basic.view.CreativeDataRateHourModel;
import com.pxene.pap.domain.model.basic.view.CreativeDataRateHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeDataRateHourDao {
    long countByExample(CreativeDataRateHourModelExample example);

    int deleteByExample(CreativeDataRateHourModelExample example);

    int insert(CreativeDataRateHourModel record);

    int insertSelective(CreativeDataRateHourModel record);

    List<CreativeDataRateHourModel> selectByExample(CreativeDataRateHourModelExample example);

    int updateByExampleSelective(@Param("record") CreativeDataRateHourModel record, @Param("example") CreativeDataRateHourModelExample example);

    int updateByExample(@Param("record") CreativeDataRateHourModel record, @Param("example") CreativeDataRateHourModelExample example);
}