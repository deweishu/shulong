package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import org.springframework.stereotype.Component;

/**
 *  一分钟内邀请2个人，持续3分钟到4分钟封号
 * @author dengjihai
 * @create 2018-10-29
 **/
@Component
public class UserInvetMinstueCache extends AbstractCache<Integer> {



    public void saveNum(String userId,Integer num){
        put(userId,num);
    }

    public Integer getNum(String userId){
        Integer userStr=get(userId);
        return userStr==null?0:userStr;
    }



    @Override
    protected String getPrefix() {
        return "user_invet_mintiues_num";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MINUTE * 1;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
