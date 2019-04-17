package com.qkl.common.cache;

/**
 * 引起缓存失效的事件
 * Created by dengjihai on 2018/3/28.
 */
public class CacheInvalidEvent {

    private String cacheName;   //引起的缓存

    private String key;         //需要失效的key值

    public CacheInvalidEvent(String cacheName, String key) {
        this.cacheName = cacheName;
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
