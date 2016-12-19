package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.InfoFlowTmplModel;
import com.pxene.pap.domain.model.basic.InfoFlowTmplModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoFlowTmplDao {
    long countByExample(InfoFlowTmplModelExample example);

    int deleteByExample(InfoFlowTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(InfoFlowTmplModel record);

    int insertSelective(InfoFlowTmplModel record);

    List<InfoFlowTmplModel> selectByExample(InfoFlowTmplModelExample example);

    InfoFlowTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InfoFlowTmplModel record, @Param("example") InfoFlowTmplModelExample example);

    int updateByExample(@Param("record") InfoFlowTmplModel record, @Param("example") InfoFlowTmplModelExample example);

    int updateByPrimaryKeySelective(InfoFlowTmplModel record);

    int updateByPrimaryKey(InfoFlowTmplModel record);
}