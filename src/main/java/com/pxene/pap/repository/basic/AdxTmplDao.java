package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.AdxTmplModel;
import com.pxene.pap.domain.models.AdxTmplModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdxTmplDao {
    long countByExample(AdxTmplModelExample example);

    int deleteByExample(AdxTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdxTmplModel record);

    int insertSelective(AdxTmplModel record);

    List<AdxTmplModel> selectByExample(AdxTmplModelExample example);

    AdxTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdxTmplModel record, @Param("example") AdxTmplModelExample example);

    int updateByExample(@Param("record") AdxTmplModel record, @Param("example") AdxTmplModelExample example);

    int updateByPrimaryKeySelective(AdxTmplModel record);

    int updateByPrimaryKey(AdxTmplModel record);
}