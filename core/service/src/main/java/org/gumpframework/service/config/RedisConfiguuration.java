package org.gumpframework.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfiguuration {

    private final RedisTemplate<Object, Object> redisTemplate = new RedisTemplate();

    public RedisConfiguuration() {
    }

    @Bean
    public RedisTemplate<?, ?> sessionRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        GenericToStringSerializer<Object> genericToStringSerializer = new GenericToStringSerializer(Object.class);
        this.redisTemplate.setConnectionFactory(redisConnectionFactory);
        this.redisTemplate.setKeySerializer(genericToStringSerializer);
        this.redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate.setHashKeySerializer(genericToStringSerializer);
        this.redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return this.redisTemplate;
    }


    @Bean(name = {"redisCacheManager"})
    @Primary
    public RedisCacheManager redisCacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

}
