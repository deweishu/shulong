package com.qkl.api.user.cache;

import com.qkl.common.cache.CacheConstant;
import com.qkl.common.cache.StringCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户Token管理
 * 用于防止多点登录
 * Created by Benson on 2018/3/1.
 */
@Component
public class UserTokenCache extends StringCache {

    private final Logger logger = LoggerFactory.getLogger(UserTokenCache.class);


    /**
     * 保存用户TOKEN
     * @param userId
     * @param token
     */
    public void saveToken(String userId, String token) {
        put(userId, token);
    }

    /**
     * 获取用户TOKEN
     * @param userId
     */
    public String getToken(String userId) {

        String token = get(userId);

        return token;
    }


    @Override
    public void invalid(String key) {
        logger.info("清除userId[{}]对应的Token数据", key);
        remove(key);
    }

    @Override
    protected String getPrefix() {
        return CacheConstant.USER_TOKEN_CACHE;
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.DAY*7;
    }

}
