package cn.pumch.web.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;

@Service
public class JedisWrap {

    private final static Logger logger = LoggerFactory.getLogger(JedisWrap.class);

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    private RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

    public void putObject(Object key, Object value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(serializer.serialize(key), serializer.serialize(value));
//            connection.set(SerializeUtil.serialize(key),SerializeUtil.serialize(value));
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
    }

    public void increase(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.incrBy(key, 1);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
    }

    public void reduce(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.incrBy(key, -1);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
    }

    public String getString(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return null;
    }

    public void setString(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
    }

    public Long addSetEle(String key, String... members) {
        Jedis jedis = getJedis();
        try {
            return jedis.sadd(key, members);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return -1l;
    }

    public Long removeSetEle(String key, String member) {
        Jedis jedis = getJedis();
        try {
            return jedis.srem(key, member);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return -1l;
    }

    public boolean isMember(String key, String ele) {
        Jedis jedis = getJedis();
        try {
            return jedis.sismember(key, ele);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return false;
    }

    public Set<String> getSetMembers(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.smembers(key);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return null;
    }

    public String getSetByKey(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.spop(key);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return null;
    }

    public Long setExpire(String key, int time) {
        Jedis jedis = getJedis();
        try {
            return jedis.expire(key, time);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return -1l;
    }

    /**
     * 向集合中添加元素
     * @param key
     * @param value
     * @return
     */
    public long addToList(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.sadd(key, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return 0;
    }

    /**
     * 从集合中移除指定元素
     * @param key
     * @param value
     * @return
     */
    public long removeFromList(String key, String value) {
        Jedis jedis = getJedis();
        try {
            return jedis.srem(key, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return 0;
    }

    /**
     * 获取列表的长度
     * @param key
     * @return
     */
    public long getListSize(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.scard(key);
        } catch (JedisConnectionException e) {
            logger.error("Redis error!", e);
            jedis.quit();
        }
        return 0;
    }

    public Jedis getJedis() {
        return jedisConnectionFactory.getShardInfo().createResource();
    }

    public JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }
}
