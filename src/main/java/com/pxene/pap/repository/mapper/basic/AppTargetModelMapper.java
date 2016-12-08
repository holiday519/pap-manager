package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AppTargetModel;
import com.pxene.pap.domain.model.basic.AppTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppTargetModelMapper {
    long countByExample(AppTargetModelExample example);

    int deleteByExample(AppTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppTargetModel record);

    int insertSelective(AppTargetModel record);

    List<AppTargetModel> selectByExample(AppTargetModelExample example);

    AppTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppTargetModel record, @Param("example") AppTargetModelExample example);

    int updateByExample(@Param("record") AppTargetModel record, @Param("example") AppTargetModelExample example);

    int updateByPrimaryKeySelective(AppTargetModel record);

    int updateByPrimaryKey(AppTargetModel record);
}