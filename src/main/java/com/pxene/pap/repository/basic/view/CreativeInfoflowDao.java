package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.CreativeInfoflowModel;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelWithBLOBs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CreativeInfoflowDao {
    long countByExample(CreativeInfoflowModelExample example);

    int deleteByExample(CreativeInfoflowModelExample example);

    int insert(CreativeInfoflowModelWithBLOBs record);

    int insertSelective(CreativeInfoflowModelWithBLOBs record);

    List<CreativeInfoflowModelWithBLOBs> selectByExampleWithBLOBs(CreativeInfoflowModelExample example);

    List<CreativeInfoflowModel> selectByExample(CreativeInfoflowModelExample example);

    int updateByExampleSelective(@Param("record") CreativeInfoflowModelWithBLOBs record, @Param("example") CreativeInfoflowModelExample example);

    int updateByExampleWithBLOBs(@Param("record") CreativeInfoflowModelWithBLOBs record, @Param("example") CreativeInfoflowModelExample example);

    int updateByExample(@Param("record") CreativeInfoflowModel record, @Param("example") CreativeInfoflowModelExample example);
}