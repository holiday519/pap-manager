package com.pxene.pap.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHelperConfig
{
    public static final String REDIS_PRIMARY = "redis.primary.";
    
    public static final String REDIS_SECONDARY = "redis.secondary.";
    
    public static final String REDIS_TERTIARY = "redis.tertiary.";
    
    public static final int DEFAULT_TIMEOUT = 10000;
 
    public static final int DEFAULT_EXPIRE = 60000;

    public static Map<String, JedisPool> pools = new HashMap<String, JedisPool>();
    
    
    public static void init(Environment env)
    {
        if (!pools.containsKey(REDIS_PRIMARY))
        {
            JedisPool primaryPool = getJedisPool(env, REDIS_PRIMARY);
            pools.put(REDIS_PRIMARY, primaryPool);
        }
        if (!pools.containsKey(REDIS_SECONDARY))
        {
            JedisPool secondaryPool = getJedisPool(env, REDIS_SECONDARY);
            pools.put(REDIS_SECONDARY, secondaryPool);
        }
        if (!pools.containsKey(REDIS_TERTIARY))
        {
            JedisPool tertiaryPool = getJedisPool(env, REDIS_TERTIARY);
            pools.put(REDIS_TERTIARY, tertiaryPool);
        }
    }
    
    
    private static JedisPool getJedisPool(Environment env, String prefix)
    {
        String ip = env.getProperty(prefix + "ip");
        int port = Integer.parseInt(env.getProperty(prefix + "port"));
        String password = env.getProperty(prefix + "password");
        int maxActive = Integer.parseInt(env.getProperty(prefix + "pool.maxActive"));
        int maxIdle = Integer.parseInt(env.getProperty(prefix + "pool.maxIdle"));
        long maxWait = Integer.parseInt(env.getProperty(prefix + "pool.maxWait"));
        boolean testOnBorrow = Boolean.parseBoolean(env.getProperty(prefix + "pool.testOnBorrow"));
        boolean testOnReturn = Boolean.parseBoolean(env.getProperty(prefix + "pool.testOnReturn"));
        
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        
        if (!StringUtils.isEmpty(password))
        {
            return new JedisPool(config, ip, port, DEFAULT_TIMEOUT, password);
        }
        else
        {
            return new JedisPool(config, ip, port, 10000);
        }
    }
}
