package com.qkl.apk.cache;

import com.qkl.apk.dto.ApkDto;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 *  分类推荐缓存
 * @author dengjihai
 * @create 2018-10-18
 **/
@Component
public class CateRecommdCache extends AbstractCache<String> {




    public void saveToRecommed(String cateId,  List<ApkDto> apkDtoList){
        String str=get(cateId);
        //如果之前有数据了，那就获取之前，然后再加入此次添加的数据
        if(StringUtil.isNotNil(str)){
            List<ApkDto> cacheApkList = JsonUtil.jsonToList(str,ApkDto.class);
            Iterator<ApkDto> cacheIterator=cacheApkList.iterator();
            while(cacheIterator.hasNext()){
                ApkDto cacheDto=cacheIterator.next();
                //不能重复添加，剔除掉重复的数据
                Iterator<ApkDto> apkDtoIterator=apkDtoList.iterator();
                while (apkDtoIterator.hasNext()){
                    ApkDto apkDto1=apkDtoIterator.next();
                    //如果有存在的，就移除缓存中的，
                    if(apkDto1.getId().equals(cacheDto.getId())){
                        cacheIterator.remove();
                        break;
                    }
                }
            }
            cacheApkList.addAll(apkDtoList);
            put(cateId,JsonUtil.beanToJson(cacheApkList));
        }else{
            //否则就是该分类第一次添加，
            put(cateId,JsonUtil.beanToJson(apkDtoList));
        }
    }


    public List<ApkDto> getRecommedListByCateId(String cateId){
        String str=get(cateId);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,ApkDto.class);
    }


    public void delete(String cateId,String id){
        String str=get(cateId);
        if(StringUtil.isNotNil(str)){
            List<ApkDto> cacheApkList = JsonUtil.jsonToList(str,ApkDto.class);
            Iterator<ApkDto> apkDtoIterator=cacheApkList.iterator();
            while (apkDtoIterator.hasNext()){
                ApkDto apkDto=apkDtoIterator.next();
                if(apkDto.getId().equals(id)){
                    apkDtoIterator.remove();
                    break;
                }
            }
            put(cateId,JsonUtil.beanToJson(cacheApkList));
        }
    }



    @Override
    protected String getPrefix() {
        return "chain_store_recommed_app";
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
