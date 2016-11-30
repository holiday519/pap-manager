package com.pxene.pap.repository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.common.beans.AccessToken;

@Repository
public class TokenDao
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    
    public void save(AccessToken token, long timeout)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            String tokenStr = mapper.writeValueAsString(token);
            stringRedisTemplate.opsForValue().set(token.getUsername(), tokenStr, timeout, TimeUnit.SECONDS);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }
    
    public AccessToken load(String username)
    {
        if (StringUtils.isEmpty(username))
        {
            return null;
        }
        
        ObjectMapper mapper = new ObjectMapper();
        AccessToken accessToken = null;
        try
        {
            String content = stringRedisTemplate.opsForValue().get(username);
            if (StringUtils.isEmpty(content))
            {
                return null;
            }
            accessToken = mapper.readValue(content, AccessToken.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return accessToken;
    }
    
    
    public void saveString(String key, String val)
    {
        if (StringUtils.isEmpty(key))
        {
            throw new IllegalArgumentException("Properties 'key' can not be empty.");
        }
        stringRedisTemplate.opsForValue().set(key, val);
    }
    
    public void loadString(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            throw new IllegalArgumentException("Properties 'key' can not be empty.");
        }
        stringRedisTemplate.opsForValue().get(key);
    }
}
