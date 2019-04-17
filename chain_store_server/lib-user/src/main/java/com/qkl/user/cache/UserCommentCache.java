package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import org.springframework.stereotype.Component;

/**
 * 缓存，用户是否可以评分该APK
 * @author dengjihai
 * @create 2018-09-13
 **/
@Component
public class UserCommentCache extends AbstractCache<Boolean> {


    public void save(String userId,String apkId,Boolean canComment){
        put(userId+"_"+apkId,canComment);
    }


    public Boolean get(String userId,String apkId) {
        Boolean str=get(userId+"_"+apkId);
        return str;
    }

    @Override
    protected String getPrefix() {
        return "chain_store_user_comment";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MONTH*1;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
