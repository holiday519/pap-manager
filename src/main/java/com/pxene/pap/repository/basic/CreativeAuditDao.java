package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CreativeAuditDao {
    long countByExample(CreativeAuditModelExample example);

    int deleteByExample(CreativeAuditModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreativeAuditModel record);

    int insertSelective(CreativeAuditModel record);

    List<CreativeAuditModel> selectByExample(CreativeAuditModelExample example);

    CreativeAuditModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreativeAuditModel record, @Param("example") CreativeAuditModelExample example);

    int updateByExample(@Param("record") CreativeAuditModel record, @Param("example") CreativeAuditModelExample example);

    int updateByPrimaryKeySelective(CreativeAuditModel record);

    int updateByPrimaryKey(CreativeAuditModel record);
}