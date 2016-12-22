package com.pxene.pap.common;

import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.pxene.pap.domain.configs.JRedisPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	
	private static JedisPool jedisPool = null;

	// 缓存生存时间 
	private static final int expire = 60000;

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(JRedisPoolConfig.MAX_ACTIVE);
		config.setMaxIdle(JRedisPoolConfig.MAX_IDLE);
		config.setMaxWaitMillis(JRedisPoolConfig.MAX_WAIT);
		config.setTestOnBorrow(JRedisPoolConfig.TEST_ON_BORROW);
		config.setTestOnReturn(JRedisPoolConfig.TEST_ON_RETURN);
		// redis如果设置了密码：
		if (StringUtils.isEmpty(JRedisPoolConfig.REDIS_PASSWORD)) {
			jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,
					JRedisPoolConfig.REDIS_PORT, 10000);
		} else {
			jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,
					JRedisPoolConfig.REDIS_PORT, 10000,
					JRedisPoolConfig.REDIS_PASSWORD);
		}
	}

	public static JedisPool getPool() {
		return jedisPool;
	}

	/**
	 * 从jedis连接池中获取获取jedis对象
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 回收jedis
	 */
	public static void close() {
		jedisPool.close();
	}

	/**
	 * 设置过期时间
	 */
	public static void expire(String key, int seconds) {
		if (seconds <= 0) {
			return;
		}
		Jedis jedis = getJedis();
		jedis.expire(key, seconds);
		close();
	}

	/**
	 * 设置默认过期时间
	 */
	public static void expire(String key) {
		expire(key, expire);
	}

	public static void set(String key, String value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key, value);
		close();
	}

	public static void set(String key, Object value) {
		if (isBlank(key))
			return;
		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), SerializeUtils.serialize(value));
		close();
	}

	public static void set(String key, int value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, long value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, float value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static void set(String key, double value) {
		if (isBlank(key))
			return;
		set(key, String.valueOf(value));
	}

	public static Float getFloat(String key) {
		if (isBlank(key))
			return null;
		return Float.valueOf(getStr(key));
	}

	public static Double getDouble(String key) {
		if (isBlank(key))
			return null;
		return Double.valueOf(getStr(key));
	}

	public static Long getLong(String key) {
		if (isBlank(key))
			return null;
		return Long.valueOf(getStr(key));
	}

	public static Integer getInt(String key) {
		if (isBlank(key))
			return null;
		return Integer.valueOf(getStr(key));
	}

	public static String getStr(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		close();
		return value;
	}

	public static Object getObj(String key) {
		if (isBlank(key))
			return null;
		Jedis jedis = getJedis();
		if(jedis.get(key.getBytes()) == null) {
			return null;
		}
		byte[] bits = jedis.get(key.getBytes());
		Object obj = SerializeUtils.unserialize(bits);
		close();
		return obj;
	}

	public static void delete(String key) {
		Jedis jedis = getJedis();
		if (jedis.get(key) != null) {
			jedis.del(key);
		}
		close();
	}
	
	public static String[] getKeys(String pattern) {
		if(isBlank(pattern)) {
			return null;
		}
		Jedis jedis = getJedis();
		Set<String> keySet = jedis.keys(pattern);
		String[] keys = new String[keySet.size()];
		int index = 0;
		for(String key : keySet) {
			keys[index] = key;
			index ++;
		}
		close();
		return keys;
	}
	
	public static void deleteByPattern(String pattern) {
		Jedis jedis = getJedis();
		String[] keys = getKeys(pattern);
		if(keys != null && keys.length != 0) {
			if(keys.length == 1) {
				jedis.del(keys[0]);
			}else {
				jedis.del(keys);
			}
		}
		close();
	}
	
	public static Set<String> sget(String key) {
		Jedis jedis = getJedis();
		close();
		return jedis.smembers(key);
	}
	
	public static void sset(String key, String... members) {
		Jedis jedis = getJedis();
		jedis.sadd(key, members);
		close();
	}
	
	public static boolean sismember(String key, String member) {
		Jedis jedis = getJedis();
		boolean res = jedis.sismember(key, member);
		close();
		return res;
	}
	
	public static void sdelete(String key, String... members) {
		Jedis jedis = getJedis();
		jedis.srem(key, members);
		close();
	}
	
	public static void hset(String key, Map<String, String> value) {
		Jedis jedis = getJedis();
		jedis.hmset(key, value);
		close();
	}
	
	public static void hdelete(String key) {
		Jedis jedis = getJedis();
		if (jedis.hgetAll(key) != null) {
			jedis.del(key);
		}
		close();
	}
	
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
	
	public static boolean exists(String key) {
		Jedis jedis = getJedis();
		boolean isexist = jedis.exists(key);
		close();
		return isexist;
	}
	
	public static void main(String[] args) {
		
	}
	
}
