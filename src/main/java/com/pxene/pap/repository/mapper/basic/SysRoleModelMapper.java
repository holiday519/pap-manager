package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.SysRoleModel;
import com.pxene.pap.domain.model.basic.SysRoleModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleModelMapper {
    int countByExample(SysRoleModelExample example);

    int deleteByExample(SysRoleModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysRoleModel record);

    int insertSelective(SysRoleModel record);

    List<SysRoleModel> selectByExample(SysRoleModelExample example);

    SysRoleModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysRoleModel record, @Param("example") SysRoleModelExample example);

    int updateByExample(@Param("record") SysRoleModel record, @Param("example") SysRoleModelExample example);

    int updateByPrimaryKeySelective(SysRoleModel record);

    int updateByPrimaryKey(SysRoleModel record);
}