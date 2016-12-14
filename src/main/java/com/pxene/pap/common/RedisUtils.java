package com.pxene.pap.common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class RedisUtils
{
    private final static int DEFAULT_EXPIRE_SECONDS = 60000;
    
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
    
    /**
     * 设置过期时间
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds)
    {
        if (seconds <= 0)
        {
            return;
        }
        stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    
    /**
     * 设置默认过期时间
     * @param key
     */
    public void expire(String key)
    {
        expire(key, DEFAULT_EXPIRE_SECONDS);
    }
    
    public void set(String key, String value)
    {
        if (StringUtils.isEmpty(key))
        {
            return;
        }
        stringRedisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 压栈
     * @param key
     * @param value
     * @return
     */
    public Long push(String key, String value)
    {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }
    
    /**
     * 出栈
     * @param key
     * @return
     */
    public String pop(String key)
    {
        return stringRedisTemplate.opsForList().leftPop(key);
    }
    
    /**
     * 入队
     * @param key
     * @param value
     * @return
     */
    public Long in(String key, String value)
    {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }
    
    /**
     * 出队
     * @param key
     * @return
     */
    public String out(String key)
    {
        return stringRedisTemplate.opsForList().leftPop(key);
    }
    
    /**
     * 栈/队列长
     * @param key
     * @return
     */
    public Long length(String key)
    {
        return stringRedisTemplate.opsForList().size(key);
    }
    
    /**
     * 范围检索
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end)
    {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }
    
    /**
     * 移除
     * @param key
     * @param i
     * @param value
     */
    public void remove(String key, long i, String value)
    {
        stringRedisTemplate.opsForList().remove(key, i, value);
    }
    
    /**
     * 检索
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index)
    {
        return stringRedisTemplate.opsForList().index(key, index);
    }
    
    /**
     * 置值
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, long index, String value)
    {
        stringRedisTemplate.opsForList().set(key, index, value);
    }
    
    /**
     * 裁剪
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, int end)
    {
        stringRedisTemplate.opsForList().trim(key, start, end);
    }
}
