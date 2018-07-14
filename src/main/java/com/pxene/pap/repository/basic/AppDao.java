package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppModelKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppDao {
    long countByExample(AppModelExample example);

    int deleteByExample(AppModelExample example);

    int deleteByPrimaryKey(AppModelKey key);

    int insert(AppModel record);

    int insertSelective(AppModel record);

    List<AppModel> selectByExample(AppModelExample example);

    AppModel selectByPrimaryKey(AppModelKey key);

    int updateByExampleSelective(@Param("record") AppModel record, @Param("example") AppModelExample example);

    int updateByExample(@Param("record") AppModel record, @Param("example") AppModelExample example);

    int updateByPrimaryKeySelective(AppModel record);

    int updateByPrimaryKey(AppModel record);
}