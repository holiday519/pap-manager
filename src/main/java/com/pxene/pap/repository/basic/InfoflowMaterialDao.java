package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.InfoflowMaterialModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InfoflowMaterialDao {
    long countByExample(InfoflowMaterialModelExample example);

    int deleteByExample(InfoflowMaterialModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(InfoflowMaterialModel record);

    int insertSelective(InfoflowMaterialModel record);

    List<InfoflowMaterialModel> selectByExample(InfoflowMaterialModelExample example);

    InfoflowMaterialModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InfoflowMaterialModel record, @Param("example") InfoflowMaterialModelExample example);

    int updateByExample(@Param("record") InfoflowMaterialModel record, @Param("example") InfoflowMaterialModelExample example);

    int updateByPrimaryKeySelective(InfoflowMaterialModel record);

    int updateByPrimaryKey(InfoflowMaterialModel record);
}