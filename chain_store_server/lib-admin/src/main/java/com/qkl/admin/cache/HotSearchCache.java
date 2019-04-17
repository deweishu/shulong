package com.qkl.admin.cache;

import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 热门搜索关键词维护
 */
@Component
public class HotSearchCache extends AbstractCache<String> {

    private final String HOT_SEARCH_KEY="hot_search_key";


    public void save(String hotSearchKey){
        String str= get(HOT_SEARCH_KEY);
        if(StringUtil.isNotNil(str)){
            str+=hotSearchKey+",";
        }else{
            str=hotSearchKey+",";
        }
        put(HOT_SEARCH_KEY,str);
    }

    /**
     * 获取所有的热搜关键词
     * @return
     */
    public List<String> getHotSearchList(){
        String str= get(HOT_SEARCH_KEY);
        if(StringUtil.isNotNil(str)){
            String strings []=str.split(",");
            return Arrays.asList(strings);
        }
        return null;
    }

    /**
     * 删除热搜关键词
     * @param hotSearchKey
     */
    public void delete(String hotSearchKey){
        String str= get(HOT_SEARCH_KEY);
        String strings []=str.split(",");
        int i = -1;
        List<String> stringList =Arrays.asList(strings);
        List<String> list = new ArrayList<>(stringList);
        for (int j = 0; j < list.size(); j++) {
            if(list.get(j).equals(hotSearchKey)){
                i=j;
            }
        }
        if(i!=-1){
            list.remove(i);
        }
        String endStr="";
        for (String s : list) {
            endStr+=s+",";
        }
        put(HOT_SEARCH_KEY,endStr);

    }


    @Override
    protected String getPrefix() {
        return "chain_store_hot_search";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.MONTH*2;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
