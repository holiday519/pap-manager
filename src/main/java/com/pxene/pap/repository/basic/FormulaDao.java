package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.FormulaModel;
import com.pxene.pap.domain.models.FormulaModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormulaDao {
    long countByExample(FormulaModelExample example);

    int deleteByExample(FormulaModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(FormulaModel record);

    int insertSelective(FormulaModel record);

    List<FormulaModel> selectByExample(FormulaModelExample example);

    FormulaModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FormulaModel record, @Param("example") FormulaModelExample example);

    int updateByExample(@Param("record") FormulaModel record, @Param("example") FormulaModelExample example);

    int updateByPrimaryKeySelective(FormulaModel record);

    int updateByPrimaryKey(FormulaModel record);
}