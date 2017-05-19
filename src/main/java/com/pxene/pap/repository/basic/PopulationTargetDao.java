package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.PopulationTargetModel;
import com.pxene.pap.domain.models.PopulationTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PopulationTargetDao {
    long countByExample(PopulationTargetModelExample example);

    int deleteByExample(PopulationTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(PopulationTargetModel record);

    int insertSelective(PopulationTargetModel record);

    List<PopulationTargetModel> selectByExample(PopulationTargetModelExample example);

    PopulationTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PopulationTargetModel record, @Param("example") PopulationTargetModelExample example);

    int updateByExample(@Param("record") PopulationTargetModel record, @Param("example") PopulationTargetModelExample example);

    int updateByPrimaryKeySelective(PopulationTargetModel record);

    int updateByPrimaryKey(PopulationTargetModel record);
}