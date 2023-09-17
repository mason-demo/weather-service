package com.mason.weather.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Configuration class for Redis-related beans.
 * Manually configure host and port
 *
 * @author: Mason
 * @date: 2023-09-18
 */
@Configuration
public class RedisTemplateConfig {

    /**
     * Redis host
     */
    @Value("${spring.redis.host}")
    private String redisHost;

    /**
     * Redis port
     */
    @Value("${spring.redis.port}")
    private int redisPort;

    /**
     * Customize connection factory, use JedisConnectionFactory impl
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(redisPort);
        return jedisConnectionFactory;
    }

    /**
     * Create StringRedisTemplate
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return stringRedisTemplate;
    }
}
