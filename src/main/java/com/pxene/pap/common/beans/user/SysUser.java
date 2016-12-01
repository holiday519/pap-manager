package com.pxene.pap.common.beans.user;

import java.io.Serializable;
import java.util.Date;


public class SysUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    
    private String username;
    
    private String password;
    
    private String realname;
    
    private String phone;

    private String email;
    
    private String remark;
    
    private Date createTime;
    
    private Date updateTime;
    
    private SysRole sysRole;

    
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
    
    public SysRole getSysRole()
    {
        return sysRole;
    }
    public void setSysRole(SysRole sysRole)
    {
        this.sysRole = sysRole;
    }
    
    
    @Override
    public String toString()
    {
        return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", realname=" + realname + ", phone=" + phone + ", email=" + email + ", remark=" + remark + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", sysRole=" + sysRole + "]";
    }
}
