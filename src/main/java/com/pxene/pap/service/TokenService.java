package com.pxene.pap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.JwtUtils;
import com.pxene.pap.domain.beans.AccessToken;
import com.pxene.pap.domain.model.basic.UserModel;
import com.pxene.pap.domain.model.basic.UserModelExample;
import com.pxene.pap.repository.mapper.basic.UserModelMapper;

@Service
public class TokenService
{
    @Autowired
    private Environment env;
    
    @Autowired
    private UserModelMapper userMapper;
    

    public UserModel loadUserByUsername(String username)
    {
        UserModelExample example = new UserModelExample();
        example.createCriteria().andNameEqualTo(username);
        
        List<UserModel> selectResult = userMapper.selectByExample(example);
        
        if (selectResult != null && !selectResult.isEmpty())
        {
            return selectResult.get(0);
        }
        else
        {
            return null;
        }
        
        //return sysUserDao.loadUserByUsername(username);
    }
    
    public AccessToken generateToken(UserModel user)
    {
        // 读取配置文件：Token加密密钥、Token默认的有效期
        String tokenSecret = env.getProperty("dmp.token.secret");
        String tokenExpiresSecondStr = env.getProperty("dmp.token.expiresSecond");
        long tokenExpiresSecond = (tokenExpiresSecondStr == null) ? 0L : Long.parseLong(tokenExpiresSecondStr);
        
        // 根据用户ID、用户名、签发时间、到期时间等信息来生成Token
        AccessToken accessToken = JwtUtils.createJWT(String.valueOf(user.getId()), user.getName(), tokenExpiresSecond * 1000, tokenSecret);
        
        return accessToken;
    }
    
    
}
