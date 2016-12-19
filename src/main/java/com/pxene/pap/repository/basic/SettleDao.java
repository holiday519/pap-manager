package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.SettleModel;
import com.pxene.pap.domain.model.basic.SettleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleDao {
    long countByExample(SettleModelExample example);

    int deleteByExample(SettleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(SettleModel record);

    int insertSelective(SettleModel record);

    List<SettleModel> selectByExample(SettleModelExample example);

    SettleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SettleModel record, @Param("example") SettleModelExample example);

    int updateByExample(@Param("record") SettleModel record, @Param("example") SettleModelExample example);

    int updateByPrimaryKeySelective(SettleModel record);

    int updateByPrimaryKey(SettleModel record);
}