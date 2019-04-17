package com.qkl.apk.cache;

import com.qkl.apk.dto.BannerDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * banner cache
 */
@Component
public class BannerCache extends AbstractCache<String> {


    private final String BANNER_CACHE_KEY="banner_cache_key";

    public void save(List<BannerDto> bannerDtoList){
        put(BANNER_CACHE_KEY, JsonUtil.beanToJson(bannerDtoList));
    }

    /**
     * 获取banner信息
     * @return
     */
    public List<BannerDto> getBannerList(){
        String str =get(BANNER_CACHE_KEY);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,BannerDto.class);
    }

    /**
     * 删除缓存banner信息
     */
    public void delete(){
        remove(BANNER_CACHE_KEY);
    }


    @Override
    protected String getPrefix() {
        return "chain_store_banner";
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
