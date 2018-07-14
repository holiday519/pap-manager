package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.InfoflowTmplModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoflowTmplDao {
    long countByExample(InfoflowTmplModelExample example);

    int deleteByExample(InfoflowTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(InfoflowTmplModel record);

    int insertSelective(InfoflowTmplModel record);

    List<InfoflowTmplModel> selectByExample(InfoflowTmplModelExample example);

    InfoflowTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InfoflowTmplModel record, @Param("example") InfoflowTmplModelExample example);

    int updateByExample(@Param("record") InfoflowTmplModel record, @Param("example") InfoflowTmplModelExample example);

    int updateByPrimaryKeySelective(InfoflowTmplModel record);

    int updateByPrimaryKey(InfoflowTmplModel record);
}