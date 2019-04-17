package com.qkl.common.cache;

import com.qkl.common.util.SerializerUtil;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

/**
 * Created by dengjihai on 2018/3/28.
 */
public abstract class HashCache extends AbstractCache<Serializable> {

    @Override
    protected void init() {
        super.init();
        // 不使用内置序列化
        redisTemplate.setHashValueSerializer(null);
    }

    public <T> void put(String mainKey, String fieldKey, T object, RedisSerializer<T> redisSerializer) {
        if (mainKey == null || fieldKey == null) {
            return;
        }
        getHashOperations(mainKey).put(fieldKey, redisSerializer.serialize(object));
    }

    @SuppressWarnings("unchecked")
    public <T> void put(String mainKey, String fieldKey, T object) {
        if (String.class.isAssignableFrom(object.getClass())) {
            put(mainKey, fieldKey, (String) object, SerializerUtil.getStringSerializer());
        } else if (Number.class.isAssignableFrom(object.getClass())) {
            put(mainKey, fieldKey, object, SerializerUtil.getJdkSerializer());
        } else {
            put(mainKey, fieldKey, object, SerializerUtil.getJsonSerializer((Class<T>) object.getClass()));
        }
    }

    public <T> T get(String mainKey, String fieldKey, RedisSerializer<T> redisSerializer) {
        if (mainKey == null || fieldKey == null) {
            return null;
        }
        byte[] tmp = (byte[]) getHashOperations(mainKey).get(fieldKey);
        if (tmp == null) {
            return null;
        }
        return redisSerializer.deserialize(tmp);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String mainKey, String fieldKey, Class<T> clazz) {
        if (String.class.isAssignableFrom(clazz)) {
            return (T) get(mainKey, fieldKey, SerializerUtil.getStringSerializer());
        } else if (Number.class.isAssignableFrom(clazz)) {
            return (T) get(mainKey, fieldKey, SerializerUtil.getJdkSerializer());
        }
        return get(mainKey, fieldKey, SerializerUtil.getJsonSerializer(clazz));
    }

    public String get(String mainKey, String fieldKey) {
        return get(mainKey, fieldKey, SerializerUtil.getStringSerializer());
    }

    public void remove(String mainKey, String fieldKey) {
        getHashOperations(mainKey).delete(fieldKey);
    }
}
