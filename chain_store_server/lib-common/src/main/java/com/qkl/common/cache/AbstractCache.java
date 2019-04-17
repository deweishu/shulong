package com.qkl.common.cache;

import com.googlecode.gentyref.GenericTypeReflector;
import com.qkl.common.util.SerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

/**
 * 抽象缓存类
 * Created by dengjihai on 2018/3/28.
 */
public abstract class AbstractCache<V extends Serializable> implements CacheInvalidListener{

    @Resource(name = "redisTemplate")
    protected RedisTemplate<String, V> redisTemplate;

    protected RedisSerializer serializer = null;

    @Autowired
    protected CacheInvalidManager cacheInvalidManager;

    /**
     * 缓存Key的前缀,使用英文冒号分隔
     */
    protected abstract String getPrefix();

    /**
     * 超时时间,单位秒
     */
    protected abstract Integer getTimeout();

    @PostConstruct
    protected void init() {
        redisTemplate.setKeySerializer(SerializerUtil.getStringSerializer());
        redisTemplate.setHashKeySerializer(SerializerUtil.getStringSerializer());

        if (serializer == null) {
            Class c = (Class) ((ParameterizedType) GenericTypeReflector.getExactSuperType(getClass(),
                    AbstractCache.class)).getActualTypeArguments()[0];

            if (String.class.isAssignableFrom(c)) {
                setSerializer(SerializerUtil.getStringSerializer());
            } else {
                setSerializer(SerializerUtil.getJsonSerializer(c));
            }
        } else {
            setSerializer(serializer);
        }
        cacheInvalidManager.registerListener(getPrefix(),this);
    }

    private void setSerializer(RedisSerializer serializer) {
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
    }

    public boolean exist(String key) {
        return redisTemplate.hasKey(getKey(key));
    }

    public void remove() {
        redisTemplate.delete(getKey(null));
    }

    public void remove(String key) {
        redisTemplate.delete(getKey(key));
    }

    protected String getKey(String key) {
        if (getPrefix() != null) {
            key = getPrefix() + ":" + key;
        }
        return key;
    }

    public void put(String key, V value) {
        if (getTimeout() != null) {
            getValueOperations(key).set(value, getTimeout(), TimeUnit.SECONDS);
        } else {
            getValueOperations(key).set(value);
        }
    }

    public V get(String key) {
        return getValueOperations(key).get();
    }

    public Long increment(String key){
        return getValueOperations(key).increment(1L);
    }


    public void expire(String key) {
        if (getTimeout() != null) {
            redisTemplate.expire(getKey(key), getTimeout(), TimeUnit.SECONDS);
        }
    }

    public void expire(String key, Integer timeout, TimeUnit timeUnit) {
        if (timeout != null) {
            redisTemplate.expire(getKey(key), timeout, TimeUnit.SECONDS);
        }
    }

    protected BoundValueOperations<String, V> getValueOperations(String key) {
        return redisTemplate.boundValueOps(getKey(key));
    }

    protected BoundHashOperations<String, String, V> getHashOperations(String key) {
        return redisTemplate.boundHashOps(getKey(key));
    }

    protected BoundListOperations<String, V> getListOperations(String key) {
        return redisTemplate.boundListOps(getKey(key));
    }

    protected BoundZSetOperations<String, V> getZSetOperations(String key) {
        return redisTemplate.boundZSetOps(getKey(key));
    }

    protected BoundSetOperations<String, V> getSetOperations(String key) {
        return redisTemplate.boundSetOps(getKey(key));
    }

    /**
     * redis缓存时间, 时间单位：秒
     */
    public static class RedisTime {
        public final static int SECOND = 1;
        public final static int MINUTE = 60;
        public final static int HOURE = 3600;
        public final static int DAY = 86400;
        public final static int MONTH = 2592000;
        public final static int YEAR = 31536000;

        /**
         * 缓存永久有效不过期
         */
        public final static Integer FOREVER = null;
    }
}
