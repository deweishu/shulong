package com.qkl.admin.cache;

import com.qkl.admin.dto.ConfigDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统配置缓存
 */
@Component
public class ConfigCache extends AbstractCache<String> {


    public void save(String configKey,ConfigDto configDto){
        put(configKey,JsonUtil.beanToJson(configDto));
    }

    /**
     * 通过config key获取缓存配置信息
     * @param configKey
     * @return
     */
    public ConfigDto getSysConfig(String configKey){
        String str=get(configKey);
        if(StringUtil.isNotNil(str)){
            ConfigDto configDto=JsonUtil.jsonToBean(str,ConfigDto.class);
            return configDto;
        }
        return null;
    }


    @Override
    protected String getPrefix() {
        return "sys_config_cache";
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
