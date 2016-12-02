package com.pxene.pap.domain.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class SysUser
{
    private String id;
    
    private String username;
    
    private String password;
    
    private String realname;
    
    private String phone;

    private String email;
    
    private String remark;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Set<SysRole> authorities = new HashSet<SysRole>(); 

    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRealname()
    {
        return realname;
    }
    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    
    @Override
    public String toString()
    {
        return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", realname=" + realname + ", phone=" + phone + ", email=" + email + ", remark=" + remark + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", authorities=" + authorities + "]";
    }
}
