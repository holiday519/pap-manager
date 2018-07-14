package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.LandpageCodeHistoryModel;
import com.pxene.pap.domain.models.LandpageCodeHistoryModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandpageCodeHistoryDao {
    long countByExample(LandpageCodeHistoryModelExample example);

    int deleteByExample(LandpageCodeHistoryModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandpageCodeHistoryModel record);

    int insertSelective(LandpageCodeHistoryModel record);

    List<LandpageCodeHistoryModel> selectByExample(LandpageCodeHistoryModelExample example);

    LandpageCodeHistoryModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandpageCodeHistoryModel record, @Param("example") LandpageCodeHistoryModelExample example);

    int updateByExample(@Param("record") LandpageCodeHistoryModel record, @Param("example") LandpageCodeHistoryModelExample example);

    int updateByPrimaryKeySelective(LandpageCodeHistoryModel record);

    int updateByPrimaryKey(LandpageCodeHistoryModel record);
}