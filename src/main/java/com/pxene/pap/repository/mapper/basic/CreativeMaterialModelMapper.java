package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeMaterialModelMapper {
    int countByExample(CreativeMaterialModelExample example);

    int deleteByExample(CreativeMaterialModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreativeMaterialModel record);

    int insertSelective(CreativeMaterialModel record);

    List<CreativeMaterialModel> selectByExample(CreativeMaterialModelExample example);

    CreativeMaterialModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreativeMaterialModel record, @Param("example") CreativeMaterialModelExample example);

    int updateByExample(@Param("record") CreativeMaterialModel record, @Param("example") CreativeMaterialModelExample example);

    int updateByPrimaryKeySelective(CreativeMaterialModel record);

    int updateByPrimaryKey(CreativeMaterialModel record);
}