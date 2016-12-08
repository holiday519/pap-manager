package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.OsTargetModel;
import com.pxene.pap.domain.model.basic.OsTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OsTargetModelMapper {
    long countByExample(OsTargetModelExample example);

    int deleteByExample(OsTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(OsTargetModel record);

    int insertSelective(OsTargetModel record);

    List<OsTargetModel> selectByExample(OsTargetModelExample example);

    OsTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OsTargetModel record, @Param("example") OsTargetModelExample example);

    int updateByExample(@Param("record") OsTargetModel record, @Param("example") OsTargetModelExample example);

    int updateByPrimaryKeySelective(OsTargetModel record);

    int updateByPrimaryKey(OsTargetModel record);
}