package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.SizeModel;
import com.pxene.pap.domain.model.basic.SizeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SizeModelMapper {
    long countByExample(SizeModelExample example);

    int deleteByExample(SizeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(SizeModel record);

    int insertSelective(SizeModel record);

    List<SizeModel> selectByExample(SizeModelExample example);

    SizeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SizeModel record, @Param("example") SizeModelExample example);

    int updateByExample(@Param("record") SizeModel record, @Param("example") SizeModelExample example);

    int updateByPrimaryKeySelective(SizeModel record);

    int updateByPrimaryKey(SizeModel record);
}