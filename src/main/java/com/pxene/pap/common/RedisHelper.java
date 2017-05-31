package com.pxene.pap.common;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;


@Component
public class RedisHelper
{
    private JedisPool jedisPool;
 
    // 缓存生存时间 
    private static final int expire = 60000;
    
    private static Properties props = new Properties();;
    
    
    static
    {
        try
        {
            props.load(RedisHelper.class.getResourceAsStream("/application.properties"));
            
            String active = props.getProperty("spring.profiles.active");
           
            String configFile = "/application-" + active + ".properties";
            
            props.clear();
            
            props.load(RedisHelper.class.getResourceAsStream(configFile));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    private RedisHelper()
    {
    }
    
    public static RedisHelper open(String prefix)
    {
        RedisHelper redisHelper = new RedisHelper();
        
        redisHelper.getJedisPool(prefix);
        
        return redisHelper;
    }
    
    /**
     * 读取配置文件，建立连接池。
     * @param prefix    redis.properties配置文件前缀
     */
    private void getJedisPool(String prefix)
    {
        String ip = props.getProperty(prefix + "ip");
        int port = Integer.parseInt(props.getProperty(prefix + "port"));
        String password = props.getProperty(prefix + "password");
        int maxActive = Integer.parseInt(props.getProperty(prefix + "pool.maxActive"));
        int maxIdle = Integer.parseInt(props.getProperty(prefix + "pool.maxIdle"));
        long maxWait = Integer.parseInt(props.getProperty(prefix + "pool.maxWait"));
        boolean testOnBorrow = Boolean.parseBoolean(props.getProperty(prefix + "pool.testOnBorrow"));
        boolean testOnReturn = Boolean.parseBoolean(props.getProperty(prefix + "pool.testOnReturn"));
        
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        
        // redis如果设置了密码：
        if (!StringUtils.isEmpty(password))
        {
            jedisPool = new JedisPool(config, ip, port, 10000, password);
        }
        else
        {
            jedisPool = new JedisPool(config, ip, port, 10000);
        }
    }
    
    /**
     * 从jedis连接池中获取获取jedis对象
     */
    public Jedis getJedis()
    {
        return jedisPool.getResource();
    }
    
    /**
     * 回收jedis
     */
    public void close(Jedis jedis)
    {
        jedis.close();
    }
    
    /**
     * 设置过期时间
     */
    public void expire(String key, int seconds)
    {
        if (seconds <= 0)
        {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        close(jedis);
    }
    
    /**
     * 设置默认过期时间
     */
    public void expire(String key)
    {
        expire(key, expire);
    }
    
    public void set(String key, String value)
    {
        if (isBlank(key))
        {
            return;
        }
        Jedis jedis = getJedis();
        jedis.set(key, value);
        close(jedis);
    }
    
    public void set(String key, Object value)
    {
        if (isBlank(key))
        {
            return;
        }
        Jedis jedis = getJedis();
        jedis.set(key.getBytes(), SerializeUtils.serialize(value));
        close(jedis);
    }
    
    public void set(String key, int value)
    {
        if (isBlank(key))
        {
            return;
        }
        set(key, String.valueOf(value));
    }
    
    public void set(String key, long value)
    {
        if (isBlank(key))
        {
            return;
        }
        set(key, String.valueOf(value));
    }
    
    public void set(String key, float value)
    {
        if (isBlank(key))
        {
            return;
        }
        set(key, String.valueOf(value));
    }
    
    public void set(String key, double value)
    {
        if (isBlank(key))
        {
            return;
        }
        set(key, String.valueOf(value));
    }
    
    public boolean setNX(String key, int value)
    {
        if (isBlank(key))
        {
            return false;
        }

        Jedis jedis = getJedis();
        Long flag = jedis.setnx(key, String.valueOf(value));
        close(jedis);
        
        if (flag > 0)
        {
            return true;
        }
        
        return false;
    }
    
    public Float getFloat(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        return Float.valueOf(getStr(key));
    }
    
    public Double getDouble(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        return Double.valueOf(getStr(key));
    }
    
    public Long getLong(String key)
    {
        if (isBlank(key))
            return null;
        return Long.valueOf(getStr(key));
    }
    
    public Integer getInt(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        return Integer.valueOf(getStr(key));
    }
    
    public String getStr(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        close(jedis);
        return value;
    }
    
    public Object getObj(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        if (jedis.get(key.getBytes()) == null)
        {
            return null;
        }
        byte[] bits = jedis.get(key.getBytes());
        Object obj = SerializeUtils.unserialize(bits);
        close(jedis);
        return obj;
    }
    
    public void delete(String key)
    {
        Jedis jedis = getJedis();
        if (jedis.get(key) != null)
        {
            jedis.del(key);
        }
        close(jedis);
    }
    
    public String[] getKeys(String pattern)
    {
        if (isBlank(pattern))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Set<String> keySet = jedis.keys(pattern);
        String[] keys = new String[keySet.size()];
        int index = 0;
        for (String key : keySet)
        {
            keys[index] = key;
            index++;
        }
        close(jedis);
        return keys;
    }
    
    public void deleteByPattern(String pattern)
    {
        Jedis jedis = getJedis();
        String[] keys = getKeys(pattern);
        if (keys != null && keys.length != 0)
        {
            if (keys.length == 1)
            {
                jedis.del(keys[0]);
            }
            else
            {
                jedis.del(keys);
            }
        }
        close(jedis);
    }
    
    public Set<String> sget(String key)
    {
        Jedis jedis = getJedis();
        close(jedis);
        return jedis.smembers(key);
    }
    
    public void sset(String key, String... members)
    {
        Jedis jedis = getJedis();
        jedis.sadd(key, members);
        close(jedis);
    }
    
    public boolean sismember(String key, String member)
    {
        Jedis jedis = getJedis();
        boolean res = jedis.sismember(key, member);
        close(jedis);
        return res;
    }
    
    public void sdelete(String key, String... members)
    {
        Jedis jedis = getJedis();
        jedis.srem(key, members);
        close(jedis);
    }
    
    public void hset(String key, Map<String, String> value)
    {
        Jedis jedis = getJedis();
        jedis.hmset(key, value);
        close(jedis);
    }
    
    public void hdelete(String key)
    {
        Jedis jedis = getJedis();
        if (jedis.hgetAll(key) != null)
        {
            jedis.del(key);
        }
        close(jedis);
    }
    
    public Map<String, String> hget(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Map<String, String> hgetAll = jedis.hgetAll(key);
        close(jedis);
        return hgetAll;
    }
    
    public Set<String> hkeys(String key)
    {
        if (isBlank(key))
        {
            return null;
        }
        Jedis jedis = getJedis();
        Set<String> hgetAll = jedis.hkeys(key);
        close(jedis);
        return hgetAll;
    }
    
    public boolean isBlank(String str)
    {
        return str == null || "".equals(str.trim());
    }
    
    public boolean exists(String key)
    {
        Jedis jedis = getJedis();
        boolean isexist = jedis.exists(key);
        close(jedis);
        return isexist;
    }
    
    public void sddKey(String key, List<String> values)
    {
        if (values != null && !values.isEmpty())
        {
            Jedis jedis = getJedis();
            for (String value : values)
            {
                jedis.sadd(key, value);
            }
            close(jedis);
        }
    }
    
    public void checkAndSetDeadLoop(String key, String value)
    {
        if (isBlank(key))
        {
            return;
        }
        
        Jedis jedis = getJedis();
        Transaction transaction = null;
        List<Object> list = null;
        
        while (true)
        {
            jedis.watch(key);
            
            transaction = jedis.multi();
            transaction.set(key, value);
            
            list = transaction.exec();
            
            if (list != null && !list.isEmpty() && list.get(0) != null && "OK".equalsIgnoreCase(list.get(0).toString()))
            {
                break;
            }
        }
        
        close(jedis);
    }
    
    public boolean checkAndSet(String key, String value)
    {
        if (isBlank(key))
        {
            return false;
        }
        
        Jedis jedis = getJedis();
        
        jedis.watch(key);
        
        Transaction transaction = jedis.multi();
        transaction.set(key, value);
        
        List<Object> list = transaction.exec();
        
        close(jedis);
        
        return checkIfAllOK(list);
    }
    
    public boolean doTransaction(Jedis jedis, String key, String value)
    {
        Transaction transaction = jedis.multi();
        transaction.set(key, value);
        
        List<Object> list = transaction.exec();
        
        close(jedis);
        
        return checkIfAllOK(list);
    }
    
    /**
     * 检查Redis一个事务中的全部操作是否都成功（即，返回值是不是都为OK）
     * @param list  事务操作的全部返回值，例如[OK, nil, OK]或[OK]
     * @return
     */
    private static boolean checkIfAllOK(List<Object> list)
    {
        if (list != null && !list.isEmpty())
        {
            for (Object object : list)
            {
                if (!"OK".equalsIgnoreCase(object.toString()))
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment。<br>
     * 如果命令执行成功，那么 key 的值会被更新为（执行加法之后的）新值，并且新值会以字符串的形式返回给调用者。
     * @param key          键名
     * @param field        域
     * @param increment    增量值，可正可负
     * @return 更新后的新值
     */
    public void hincrbyFloat(String key, String field, float increment) 
    {
        if (isBlank(key))
        {
            throw new IllegalArgumentException();
        }
        
        Jedis jedis = getJedis();
        jedis.hincrByFloat(key, field, increment);
        close(jedis);
    }
    
    public void incrybyInt(String key, int increment)
    {
        if (isBlank(key))
        {
            throw new IllegalArgumentException();
        }
        
        Jedis jedis = getJedis();
        jedis.incrBy(key, increment);
        close(jedis);
    }
    
    public void incrybyDouble(String key, double increment)
    {
        if (isBlank(key))
        {
            throw new IllegalArgumentException();
        }
        
        Jedis jedis = getJedis();
        jedis.incrByFloat(key, increment);
        close(jedis);
    }
}
