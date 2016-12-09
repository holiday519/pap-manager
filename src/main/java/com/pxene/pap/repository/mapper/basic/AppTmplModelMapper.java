package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AppTmplModel;
import com.pxene.pap.domain.model.basic.AppTmplModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppTmplModelMapper {
    int countByExample(AppTmplModelExample example);

    int deleteByExample(AppTmplModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AppTmplModel record);

    int insertSelective(AppTmplModel record);

    List<AppTmplModel> selectByExample(AppTmplModelExample example);

    AppTmplModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppTmplModel record, @Param("example") AppTmplModelExample example);

    int updateByExample(@Param("record") AppTmplModel record, @Param("example") AppTmplModelExample example);

    int updateByPrimaryKeySelective(AppTmplModel record);

    int updateByPrimaryKey(AppTmplModel record);
}