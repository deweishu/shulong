package com.qkl.apk.cache;

import com.qkl.apk.dto.CategoryDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * app 应用分类管理
 */
@Component
public class CategoryCache extends AbstractCache<String> {


    private final String CATEGORY_CACHE_KEY="category_cache_key";

    public void saveCate(List<CategoryDto> categoryDtoList){
        put(CATEGORY_CACHE_KEY, JsonUtil.beanToJson(categoryDtoList));
    }


    public List<CategoryDto> getCategoryList(){

        String str=get(CATEGORY_CACHE_KEY);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,CategoryDto.class);

    }

    public void deleteCategory(){
        remove(CATEGORY_CACHE_KEY);
    }



    @Override
    protected String getPrefix() {
        return "app_category";
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
