package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import org.springframework.stereotype.Component;

/**
 * @author dengjihai
 * @create 2018-04-28
 **/
@Component
public class SignCache extends AbstractCache<String> {


    public void save(String userId,String date){
        put(userId,date);
    }

    public String getSign(String userId){
        String str=get(userId);
        return str;
    }



    @Override
    protected String getPrefix() {
        return "sign_in";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.DAY*30;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
