package com.qkl.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import javax.annotation.PostConstruct;

/**
 * 本地缓存
 * Created by dengjihai on 2018/3/28.
 */
public abstract class AbstractLocalCache<V> {

    Cache<String, V> localCache;

    /**
     * 超时时间，单位秒，NULL为不超时
     */
    protected abstract CacheExpire getTimeout();

    @PostConstruct
    void init() {
        Caffeine caffeine = Caffeine.newBuilder();
        if (getTimeout() != null) {
            switch (getTimeout().getExpireType()) {
                case AFTER_WRITE:
                    caffeine.expireAfterWrite(getTimeout().getDuration(), getTimeout().getTimeUnit());
                    break;
                case AFTER_READ:
                    caffeine.expireAfterAccess(getTimeout().getDuration(), getTimeout().getTimeUnit());
                    break;
            }
        }
        localCache = caffeine.build();

    }

    public boolean exist(String key) {
        return localCache.getIfPresent(key) != null;
    }

    public V get(String key) {
        return localCache.getIfPresent(key);
    }

    public void put(String key, V value) {
        localCache.put(key, value);
    }

    public void remove(String key) {
        localCache.invalidate(key);
    }

    public void cleanLocalCache() {
        localCache.invalidateAll();
    }
}
