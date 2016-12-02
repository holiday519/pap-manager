package com.pxene.pap.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.SysUser;

@Repository
public class SysUserDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public SysUser loadUserByUsername(String username)
    {
        String sql = "SELECT * FROM sys_user WHERE username = ?";
        
        SysUser sysUser = jdbcTemplate.queryForObject(sql, new Object[]{username}, new RowMapper<SysUser>()
        {

            @Override
            public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException
            {
                SysUser sysUser = new SysUser();
                
                sysUser.setId(rs.getString("id"));
                sysUser.setUsername(rs.getString("username"));
                sysUser.setPassword(rs.getString("password"));
                sysUser.setRealname(rs.getString("realname"));
                sysUser.setPhone(rs.getString("phone"));
                sysUser.setEmail(rs.getString("email"));
                sysUser.setRemark(rs.getString("remark"));
                sysUser.setCreateTime(rs.getDate("createtime"));
                sysUser.setUpdateTime(rs.getDate("updatetime"));
                
                return sysUser;
            }
            
        });
        
        return sysUser;
    }
    
    
    
    
}
