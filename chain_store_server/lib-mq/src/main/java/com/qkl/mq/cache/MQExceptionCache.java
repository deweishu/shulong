package com.qkl.mq.cache;

import com.qkl.common.cache.StringCache;
import org.springframework.stereotype.Component;


@Component
public class MQExceptionCache extends StringCache {

    @Override
    protected String getPrefix() {
        return "mq_exception";
    }

    @Override
    protected Integer getTimeout() {
        return 1 * RedisTime.MINUTE;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
