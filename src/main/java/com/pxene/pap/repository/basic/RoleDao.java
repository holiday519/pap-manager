package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.RoleModel;
import com.pxene.pap.domain.models.RoleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleDao {
    long countByExample(RoleModelExample example);

    int deleteByExample(RoleModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RoleModel record);

    int insertSelective(RoleModel record);

    List<RoleModel> selectByExample(RoleModelExample example);

    RoleModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoleModel record, @Param("example") RoleModelExample example);

    int updateByExample(@Param("record") RoleModel record, @Param("example") RoleModelExample example);

    int updateByPrimaryKeySelective(RoleModel record);

    int updateByPrimaryKey(RoleModel record);
}