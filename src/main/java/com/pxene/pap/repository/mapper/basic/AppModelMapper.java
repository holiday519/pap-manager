package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AppModel;
import com.pxene.pap.domain.model.basic.AppModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppModelMapper {
    long countByExample(AppModelExample example);

    int deleteByExample(AppModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppModel record);

    int insertSelective(AppModel record);

    List<AppModel> selectByExampleWithBLOBs(AppModelExample example);

    List<AppModel> selectByExample(AppModelExample example);

    AppModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppModel record, @Param("example") AppModelExample example);

    int updateByExampleWithBLOBs(@Param("record") AppModel record, @Param("example") AppModelExample example);

    int updateByExample(@Param("record") AppModel record, @Param("example") AppModelExample example);

    int updateByPrimaryKeySelective(AppModel record);

    int updateByPrimaryKeyWithBLOBs(AppModel record);

    int updateByPrimaryKey(AppModel record);
}