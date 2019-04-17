package com.qkl.apk.cache;

import com.qkl.apk.dto.ApkDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 *  应用专题下的apk 缓存
 * @author dengjihai
 * @create 2018-09-04
 **/
@Component
public class CateZtCache extends AbstractCache<String> {


    private static final String CATE_ZT_CACHE_KEY="cache_zt_key_";


    public void save(String ztId, List<ApkDto> apkDtoList){
        put(CATE_ZT_CACHE_KEY+ztId, JsonUtil.beanToJson(apkDtoList));
    }

    public List<ApkDto> getApkListByZt(String ztId){
        String str=get(CATE_ZT_CACHE_KEY+ztId);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,ApkDto.class);
    }

    public void delZtCache(String ztId){
        remove(CATE_ZT_CACHE_KEY+ztId);
    }


    public void delApk(String ztId,String apkId){
        String str=get(CATE_ZT_CACHE_KEY+ztId);
        if(StringUtil.isNotNil(str)){
            List<ApkDto> apkDtoList=JsonUtil.jsonToList(str,ApkDto.class);
            Iterator<ApkDto> apkDtoIterator=apkDtoList.iterator();
            while(apkDtoIterator.hasNext()){
                ApkDto apkDto=apkDtoIterator.next();
                if(apkDto.getId().equals(apkId)){
                    apkDtoIterator.remove();
                    break;
                }
            }
            put(CATE_ZT_CACHE_KEY+ztId,JsonUtil.beanToJson(apkDtoList));
        }
    }


    @Override
    protected String getPrefix() {
        return "chain_store_zt_app";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MONTH*6;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
