package com.zxh.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 11:35
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置hash的序列方式
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());

        // 设置其他数据类型的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());


        template.afterPropertiesSet();
        return template;
    }
}
