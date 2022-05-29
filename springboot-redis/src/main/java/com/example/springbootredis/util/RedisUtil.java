package com.example.springbootredis.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 17:30
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

    private static final Long SUCCESS = 1L;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取锁
     *
     * @param lockKey
     * @param value
     * @param expireTime：单位-秒
     * @return
     */
    public boolean getLock(String lockKey, Object value, String expireTime) {
        boolean ret = false;
        try {
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value, expireTime);
            log.info("result:{}", result);
            if (SUCCESS.equals(result)) {
                return true;
            }

        } catch (Exception e) {
            log.error("getLock error:{}", e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
        if (SUCCESS.equals(result)) {
            return true;
        }

        return false;

    }

    /**
     * 设置一个自增的数据
     *
     * @param key
     * @param num
     */
    public Long incr(String key, Long num) {
        return redisTemplate.opsForValue().increment(key, num);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

}
