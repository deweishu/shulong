package com.qkl.msg.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.msg.dto.UserMsgDto;
import com.qkl.msg.entity.UserMsg;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dengjihai
 * @create 2018-10-31
 **/
@Component
public class UserMsgCache extends AbstractCache<String> {


    public void save(String userId, List<UserMsgDto> msgDtoList){
        put(userId, JsonUtil.beanToJson(msgDtoList));
    }

    public List<UserMsgDto> getByUserId(String userId){
        String str=get(userId);
        if(str==null){
            return null;
        }
        return JsonUtil.jsonToList(str,UserMsgDto.class);
    }


    @Override
    protected String getPrefix() {
        return "chain_store_user_msg";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.FOREVER;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
