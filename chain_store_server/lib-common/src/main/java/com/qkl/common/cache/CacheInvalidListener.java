package com.qkl.common.cache;

/**
 * 引起缓存失效的的监听
 * Created by dengjihai on 2018/3/28.
 */
public interface CacheInvalidListener {

    /**
     * 触发失效
     * @param key 需要失效的key值
     */
    public void invalid(String key);

}
