package com.qkl.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 引起缓存失效的管理器
 * Created by dengjihai on 2018/3/28.
 */
@Component
public class CacheInvalidManager extends AbstractLocalCache<CacheInvalidListener>{
    private static final Logger log = LoggerFactory.getLogger(CacheInvalidManager.class);

    @Override
    protected CacheExpire getTimeout() {
        return null;
    }

    /**
     * 注册监听
     */
    public  void registerListener(String name, CacheInvalidListener listener){
        if(exist(name)){
            log.error("命名冲突："+name);
        }else{
            put(name,listener);
        }
    }

    /**
     * 发布事件
     */
    public void publish(CacheInvalidEvent event){
        String name = event.getCacheName();
        CacheInvalidListener listener = get(name);
        if(listener == null){
            log.error("未找到缓存name:"+name);
        }else{
            listener.invalid(event.getKey());
        }
    }

}
