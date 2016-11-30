package com.pxene.pap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pxene.pap.common.beans.user.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>
{
    public SysUser findByUsername(String username);
}
