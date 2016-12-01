package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.beans.user.SysUser;
import com.pxene.pap.repository.dao.SysUserDao;

@Service
public class SysUserService
{
    @Autowired
    private SysUserDao sysUserDao;

    
    public SysUser loadUserByUsername(String username)
    {
        return sysUserDao.loadUserByUsername(username);
    }
}
