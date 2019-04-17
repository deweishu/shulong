package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

/**
 * 连续签到天数 cache
 * @author dengjihai
 * @create 2018-08-29
 **/
@Component
public class SignDayCache extends AbstractCache<String> {



    public void save(String userId,Integer dayNum){
        put(userId,dayNum+"");
    }

    /**
     * 获取累计签到天数
     * @param userId
     * @return
     */
    public Integer getDayNum(String userId){
        String str= get(userId);
        if(StringUtil.isNil(str)){
            return 0;
        }
        return Integer.parseInt(str);
    }

    @Override
    protected String getPrefix() {
        return "sign_day_user";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.DAY*7;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
