package com.pxene.pap.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Component
public class RedisHelperConfig
{
    private static final String REDIS_TERTIARY = "redis.tertiary.";

    private static final String REDIS_SECONDARY = "redis.secondary.";

    private static final String REDIS_PRIMARY = "redis.primary.";

    private static final int DEFAULT_TIMEOUT = 10000;
 
    private Environment env;
    
    public static Map<String, JedisPool> pools = new HashMap<String, JedisPool>();
    
    
    @Autowired
    public RedisHelperConfig(Environment env)
    {
        this.env = env;
        
        if (!pools.containsKey(REDIS_PRIMARY))
        {
            JedisPool primaryPool = getJedisPool(REDIS_PRIMARY);
            pools.put(REDIS_PRIMARY, primaryPool);
        }
        if (!pools.containsKey(REDIS_SECONDARY))
        {
            JedisPool secondaryPool = getJedisPool(REDIS_SECONDARY);
            pools.put(REDIS_SECONDARY, secondaryPool);
        }
        if (!pools.containsKey(REDIS_TERTIARY))
        {
            JedisPool tertiaryPool = getJedisPool(REDIS_TERTIARY);
            pools.put(REDIS_TERTIARY, tertiaryPool);
        }
    }


    private JedisPool getJedisPool(String prefix)
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
