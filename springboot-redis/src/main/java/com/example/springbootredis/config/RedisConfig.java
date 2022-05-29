package com.example.springbootredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <h3>atd-pub</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-03-31 11:37
 **/
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 这个地方不可使用 json 序列化，否则会有问题，会出现一个 java.lang.IllegalArgumentException: Value must not be null! 错误
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
