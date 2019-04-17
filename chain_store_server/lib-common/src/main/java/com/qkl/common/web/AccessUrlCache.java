package com.qkl.common.web;

import com.qkl.common.cache.StringCache;
import org.springframework.stereotype.Component;

/**
 * Created by dengjihai on 2017/8/5.
 * 访问路径记录
 */
@Component
public class AccessUrlCache extends StringCache {

    /**
     * 获取缓存的URL信息
     * @param sessionId
     * @return
     */
    public String getAccessUrl(String sessionId){
        return get(sessionId);
    }

    /**
     * 设置URL缓存信息
     * @param sessionId
     * @param url
     * @return
     */
    public void setAccessUrl(String sessionId, String url){
        put(sessionId, url);
    }


    @Override
    protected String getPrefix() {
        return "access:url";
    }

    /**
     * 超时时间5分钟
     * @return
     */
    @Override
    protected Integer getTimeout() {
        return 300;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
