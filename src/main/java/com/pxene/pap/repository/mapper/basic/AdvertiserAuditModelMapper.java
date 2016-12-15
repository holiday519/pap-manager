package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AdvertiserAuditModel;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertiserAuditModelMapper {
    int countByExample(AdvertiserAuditModelExample example);

    int deleteByExample(AdvertiserAuditModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdvertiserAuditModel record);

    int insertSelective(AdvertiserAuditModel record);

    List<AdvertiserAuditModel> selectByExample(AdvertiserAuditModelExample example);

    AdvertiserAuditModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdvertiserAuditModel record, @Param("example") AdvertiserAuditModelExample example);

    int updateByExample(@Param("record") AdvertiserAuditModel record, @Param("example") AdvertiserAuditModelExample example);

    int updateByPrimaryKeySelective(AdvertiserAuditModel record);

    int updateByPrimaryKey(AdvertiserAuditModel record);
}