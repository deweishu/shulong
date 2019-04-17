package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.user.dto.CandyLogDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 糖果日志缓存
 * @author dengjihai
 * @create 2018-08-29
 **/
@Component
public class CandyLogCache extends AbstractCache<String> {


    public void save(String userId, List<CandyLogDto> candyLogDtos){
        put(userId, JsonUtil.beanToJson(candyLogDtos));
    }

    public List<CandyLogDto> getCandyLog(String userId){
        String str=get(userId);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,CandyLogDto.class);
    }

    public void removeCandy(String userId){
        remove(userId);
    }

    @Override
    protected String getPrefix() {
        return "user_candy_log";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.DAY*3;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
