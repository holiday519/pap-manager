package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.PopulationModel;
import com.pxene.pap.domain.models.PopulationModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PopulationDao {
    long countByExample(PopulationModelExample example);

    int deleteByExample(PopulationModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(PopulationModel record);

    int insertSelective(PopulationModel record);

    List<PopulationModel> selectByExample(PopulationModelExample example);

    PopulationModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PopulationModel record, @Param("example") PopulationModelExample example);

    int updateByExample(@Param("record") PopulationModel record, @Param("example") PopulationModelExample example);

    int updateByPrimaryKeySelective(PopulationModel record);

    int updateByPrimaryKey(PopulationModel record);
}