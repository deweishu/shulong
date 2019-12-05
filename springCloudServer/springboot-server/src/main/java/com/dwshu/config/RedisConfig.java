package com.dwshu.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.EnumSetSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author dwshu
 * @create 2019/12/1
 */
@Configuration
public class RedisConfig {

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheManager redisCacheManager = RedisCacheManager.create(redisConnectionFactory);
        return  redisCacheManager;
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置序列化
        setSerializer(redisTemplate);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

    private void setSerializer(RedisTemplate<String, Object> redisTemplate) {
        //Jackson2JsonRedisSerializer<Object>，用于序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //StringRedisSerializer，用于序列化redis的key键
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //string类型的key采用字符串的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //hash类型的key也采用字符串的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //string类型的value采用jackson方式序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //hash类型的value采用jackson方式序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    }

}
