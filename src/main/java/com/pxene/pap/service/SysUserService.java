package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.JwtUtils;
import com.pxene.pap.domain.beans.AccessToken;
import com.pxene.pap.domain.beans.SysUser;
import com.pxene.pap.repository.SysUserDao;

@Service
public class SysUserService
{
    @Autowired
    private Environment env;
    
    @Autowired
    private SysUserDao sysUserDao;
    

    public SysUser loadUserByUsername(String username)
    {
        return sysUserDao.loadUserByUsername(username);
    }
    
    public AccessToken generateToken(SysUser user)
    {
        // 读取配置文件：Token加密密钥、Token默认的有效期
        String tokenSecret = env.getProperty("dmp.token.secret");
        String tokenExpiresSecondStr = env.getProperty("dmp.token.expiresSecond");
        long tokenExpiresSecond = (tokenExpiresSecondStr == null) ? 0L : Long.parseLong(tokenExpiresSecondStr);
        
        // 根据用户ID、用户名、签发时间、到期时间等信息来生成Token
        AccessToken accessToken = JwtUtils.createJWT(String.valueOf(user.getId()), user.getUsername(), tokenExpiresSecond * 1000, tokenSecret);
        
        return accessToken;
    }
    
    
}
