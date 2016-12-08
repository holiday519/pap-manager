package com.pxene.pap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.SysRole;

@Repository
public interface SysRoleRepository extends CrudRepository<SysRole, Long>
{
    
}
