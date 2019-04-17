package com.qkl.apk.cache;

import com.qkl.apk.dto.VersionDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import org.springframework.stereotype.Component;

/**
 * apk 的最新版本信息缓存
 * @author dengjihai
 * @create 2018-08-30
 **/
@Component
public class VersionCache extends AbstractCache<String> {


    public void saveVersion(String apkId, VersionDto versionDto){
        put(apkId, JsonUtil.beanToJson(versionDto));
    }

    public VersionDto getVersion(String apkId){
        String str =get(apkId);
        return str==null?null:JsonUtil.jsonToBean(str,VersionDto.class);
    }


    public void delete(String apkId){
        remove(apkId);
    }

    @Override
    protected String getPrefix() {
        return "chain_store_version";
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
