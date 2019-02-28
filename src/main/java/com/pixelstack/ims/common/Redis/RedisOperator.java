package com.pixelstack.ims.common.Redis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisOperator {

	//@Resource
    //private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Key（键），简单的key-value操作

    /**
     * 实现命令：TTL key，以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     *
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 实现命令：expire 设置过期时间，单位秒
     *
     * @param key
     * @return
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：INCR key，增加key一次
     *
     * @param key
     * @return
     */
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 实现命令：DEL key，删除一个key
     *
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 实现命令：set key
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 实现命令：判断键是否存在
     * @param key
     * @return
     */
    public Boolean exit(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 实现命令：获取指定键的值
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}


