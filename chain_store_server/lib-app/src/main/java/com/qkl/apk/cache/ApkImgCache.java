package com.qkl.apk.cache;

import com.qkl.apk.dto.ImgDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dengjihai
 * @create 2018-09-07
 **/
@Component
public class ApkImgCache extends AbstractCache<String> {


    public void save(String apkId, List<ImgDto> imgDtoList){
        put(apkId, JsonUtil.beanToJson(imgDtoList));
    }

    public List<ImgDto> getImgList(String apkId){
        String str=get(apkId);
        return str==null?null:JsonUtil.jsonToList(str,ImgDto.class);
    }

    @Override
    protected String getPrefix() {
        return "apk_img_cache";
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
