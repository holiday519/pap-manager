package com.pxene.pap.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.common.JwtUtils;
import com.pxene.pap.domain.beans.AccessTokenBean;
import com.pxene.pap.domain.model.basic.UserModel;
import com.pxene.pap.domain.model.basic.UserModelExample;
import com.pxene.pap.repository.basic.UserDao;

@Service
public class TokenService
{
    @Autowired
    private UserDao userDao;
    
    private String tokenSecret;
    private String tokenExpiresSecondStr;
    private long tokenExpiresSecond;
    
    
    @Autowired
    public TokenService(Environment env)
    {
        // 读取配置文件：Token加密密钥、Token默认的有效期
        tokenSecret = env.getProperty("dmp.token.secret");
        tokenExpiresSecondStr = env.getProperty("dmp.token.expiresSecond");
        tokenExpiresSecond = (tokenExpiresSecondStr == null) ? 0L : Long.parseLong(tokenExpiresSecondStr);
    }

    public UserModel loadUserByUsername(String username)
    {
        UserModelExample example = new UserModelExample();
        example.createCriteria().andNameEqualTo(username);
        
        List<UserModel> selectResult = userDao.selectByExample(example);
        
        if (selectResult != null && !selectResult.isEmpty())
        {
            return selectResult.get(0);
        }
        else
        {
            return null;
        }
    }
    
    public AccessTokenBean generateToken(UserModel user)
    {
        // 根据用户ID、用户名、签发时间、到期时间等信息来生成Token
        AccessTokenBean accessToken = JwtUtils.createJWT(String.valueOf(user.getId()), tokenExpiresSecond * 1000, tokenSecret);
        
        return accessToken;
    }
    
    public void saveToken(AccessTokenBean token)
    {
        saveToken(token, tokenExpiresSecond);
    }
    public void saveToken(AccessTokenBean token, long timeout)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            String tokenStr = mapper.writeValueAsString(token);
            JedisUtils.set(token.getUserid(), tokenStr);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }
    
    public AccessTokenBean getToken(String username)
    {
        if (StringUtils.isEmpty(username))
        {
            return null;
        }
        
        ObjectMapper mapper = new ObjectMapper();
        AccessTokenBean accessToken = null;
        try
        {
            String content = JedisUtils.getStr(username);
            if (StringUtils.isEmpty(content))
            {
                return null;
            }
            accessToken = mapper.readValue(content, AccessTokenBean.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return accessToken;
    }

    public void deleteToken(String userid)
    {
    	JedisUtils.delete(userid);
    }
}
