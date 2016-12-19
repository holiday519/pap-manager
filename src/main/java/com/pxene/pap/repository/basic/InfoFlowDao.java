package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.InfoFlowModel;
import com.pxene.pap.domain.model.basic.InfoFlowModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoFlowDao {
    long countByExample(InfoFlowModelExample example);

    int deleteByExample(InfoFlowModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(InfoFlowModel record);

    int insertSelective(InfoFlowModel record);

    List<InfoFlowModel> selectByExample(InfoFlowModelExample example);

    InfoFlowModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InfoFlowModel record, @Param("example") InfoFlowModelExample example);

    int updateByExample(@Param("record") InfoFlowModel record, @Param("example") InfoFlowModelExample example);

    int updateByPrimaryKeySelective(InfoFlowModel record);

    int updateByPrimaryKey(InfoFlowModel record);
}