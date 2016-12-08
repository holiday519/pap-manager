package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.SysRole;

@Service
public class SysRoleService
{

    public List<SysRole> getUserRole(String id)
    {
        List<SysRole> roles = new ArrayList<>();
        SysRole sysRole = new SysRole();
        sysRole.setId(111L);
        sysRole.setName("ADMIN");
        roles.add(sysRole);
        return roles;
    }
    
}
