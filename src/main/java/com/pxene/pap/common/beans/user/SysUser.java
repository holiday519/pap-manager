package com.pxene.pap.common.beans.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class SysUser
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "realname")
    private String realname;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "user_type", nullable = false, columnDefinition = "int default 0")
    private int type;
    
    @Column(name = "status", nullable = false, columnDefinition = "int default 0")
    private int status;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date createTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updateTime;
    
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<SysRole> roles;
    
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
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
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public List<SysRole> getRoles()
    {
        return roles;
    }
    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }
    
    
    @Override
    public String toString()
    {
        return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", realname=" + realname + ", email=" + email + ", phone=" + phone + ", address=" + address + ", type="
                + type + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime + ", roles=" + roles + "]";
    }
    
    @PrePersist
    protected void onCreate()
    {
        createTime = updateTime = new Date();
    }
    
    @PreUpdate
    protected void onUpdate()
    {
        updateTime = new Date();
    }
}
