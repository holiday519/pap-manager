package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.TmplVideoDetailModel;
import com.pxene.pap.domain.models.view.TmplVideoDetailModelExample;
import com.pxene.pap.domain.models.view.TmplVideoDetailModelWithBLOBs;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TmplVideoDetailDao {
    long countByExample(TmplVideoDetailModelExample example);

    int deleteByExample(TmplVideoDetailModelExample example);

    int insert(TmplVideoDetailModelWithBLOBs record);

    int insertSelective(TmplVideoDetailModelWithBLOBs record);

    List<TmplVideoDetailModelWithBLOBs> selectByExampleWithBLOBs(TmplVideoDetailModelExample example);

    List<TmplVideoDetailModel> selectByExample(TmplVideoDetailModelExample example);

    int updateByExampleSelective(@Param("record") TmplVideoDetailModelWithBLOBs record, @Param("example") TmplVideoDetailModelExample example);

    int updateByExampleWithBLOBs(@Param("record") TmplVideoDetailModelWithBLOBs record, @Param("example") TmplVideoDetailModelExample example);

    int updateByExample(@Param("record") TmplVideoDetailModel record, @Param("example") TmplVideoDetailModelExample example);
}