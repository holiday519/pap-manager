package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.TmplImageDetailModel;
import com.pxene.pap.domain.models.view.TmplImageDetailModelExample;
import com.pxene.pap.domain.models.view.TmplImageDetailModelWithBLOBs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TmplImageDetailDao {
    long countByExample(TmplImageDetailModelExample example);

    int deleteByExample(TmplImageDetailModelExample example);

    int insert(TmplImageDetailModelWithBLOBs record);

    int insertSelective(TmplImageDetailModelWithBLOBs record);

    List<TmplImageDetailModelWithBLOBs> selectByExampleWithBLOBs(TmplImageDetailModelExample example);

    List<TmplImageDetailModel> selectByExample(TmplImageDetailModelExample example);

    int updateByExampleSelective(@Param("record") TmplImageDetailModelWithBLOBs record, @Param("example") TmplImageDetailModelExample example);

    int updateByExampleWithBLOBs(@Param("record") TmplImageDetailModelWithBLOBs record, @Param("example") TmplImageDetailModelExample example);

    int updateByExample(@Param("record") TmplImageDetailModel record, @Param("example") TmplImageDetailModelExample example);
}