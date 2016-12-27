package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.CreativeDataHourModel;
import com.pxene.pap.domain.model.basic.CreativeDataHourModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeDataHourDao {
    long countByExample(CreativeDataHourModelExample example);

    int deleteByExample(CreativeDataHourModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CreativeDataHourModel record);

    int insertSelective(CreativeDataHourModel record);

    List<CreativeDataHourModel> selectByExample(CreativeDataHourModelExample example);

    CreativeDataHourModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CreativeDataHourModel record, @Param("example") CreativeDataHourModelExample example);

    int updateByExample(@Param("record") CreativeDataHourModel record, @Param("example") CreativeDataHourModelExample example);

    int updateByPrimaryKeySelective(CreativeDataHourModel record);

    int updateByPrimaryKey(CreativeDataHourModel record);
}