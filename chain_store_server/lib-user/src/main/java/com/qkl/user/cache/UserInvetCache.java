package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import org.springframework.stereotype.Component;

/**
 * 用户邀请人数 --- 半个小时之内，不能超过30人
 * @author dengjihai
 * @create 2018-10-29
 **/
@Component
public class UserInvetCache extends AbstractCache<Integer> {



    public void saveNum(String userId,Integer num){
        put(userId,num);
    }

    public Integer getNum(String userId){
        Integer userStr=get(userId);
        return userStr==null?0:userStr;
    }



    @Override
    protected String getPrefix() {
        return "user_invet_num";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MINUTE * 30;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
