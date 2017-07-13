package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AppTargetDetailModel;
import com.pxene.pap.domain.models.AppTargetDetailModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppTargetDetailDao {
    long countByExample(AppTargetDetailModelExample example);

    int deleteByExample(AppTargetDetailModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppTargetDetailModel record);

    int insertSelective(AppTargetDetailModel record);

    List<AppTargetDetailModel> selectByExample(AppTargetDetailModelExample example);

    AppTargetDetailModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppTargetDetailModel record, @Param("example") AppTargetDetailModelExample example);

    int updateByExample(@Param("record") AppTargetDetailModel record, @Param("example") AppTargetDetailModelExample example);

    int updateByPrimaryKeySelective(AppTargetDetailModel record);

    int updateByPrimaryKey(AppTargetDetailModel record);
}