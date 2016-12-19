package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.IndustryModel;
import com.pxene.pap.domain.model.basic.IndustryModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndustryDao {
    long countByExample(IndustryModelExample example);

    int deleteByExample(IndustryModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IndustryModel record);

    int insertSelective(IndustryModel record);

    List<IndustryModel> selectByExample(IndustryModelExample example);

    IndustryModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IndustryModel record, @Param("example") IndustryModelExample example);

    int updateByExample(@Param("record") IndustryModel record, @Param("example") IndustryModelExample example);

    int updateByPrimaryKeySelective(IndustryModel record);

    int updateByPrimaryKey(IndustryModel record);
}