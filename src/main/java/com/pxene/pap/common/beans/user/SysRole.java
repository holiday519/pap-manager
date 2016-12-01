package com.pxene.pap.common.beans.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class SysRole
{
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setRoleName(String name)
    {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return "Role [id=" + id + ", name=" + name + "]";
    }
}
