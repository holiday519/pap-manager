package com.pxene.pap.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    
    public Long increment(String key, String field, long increment)
    {
        return stringRedisTemplate.opsForHash().increment(key, field, increment);
    }
    
    public void delete(String key)
    {
        stringRedisTemplate.delete(key);
    }
}
