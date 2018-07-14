package com.pxene.pap.domain.configs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class JRedisPoolConfig {
	
	private static Properties props = new Properties();
	static {
		try {
			props.load(JRedisPoolConfig.class.getResourceAsStream("/redis.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static final String REDIS_IP = props.getProperty("redis.ip");
    public static final int REDIS_PORT = Integer.parseInt(props.getProperty("redis.port"));
    public static final String REDIS_PASSWORD = props.getProperty("redis.password");
    public static final int MAX_ACTIVE = Integer.parseInt(props.getProperty("redis.pool.maxActive"));
    public static final int MAX_IDLE = Integer.parseInt(props.getProperty("redis.pool.maxIdle"));
    public static final long MAX_WAIT = Integer.parseInt(props.getProperty("redis.pool.maxWait"));
    public static final boolean TEST_ON_BORROW = Boolean.parseBoolean(props.getProperty("redis.pool.testOnBorrow"));
    public static final boolean TEST_ON_RETURN = Boolean.parseBoolean(props.getProperty("redis.pool.testOnReturn"));
    
}