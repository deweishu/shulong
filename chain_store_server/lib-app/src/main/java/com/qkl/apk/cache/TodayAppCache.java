package com.qkl.apk.cache;

import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.entity.Apk;
import com.qkl.common.cache.AbstractCache;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;

/**
 *
 * 今日主题的缓存信息
 * @author dengjihai
 * @create 2018-09-11
 **/
@Component
public class TodayAppCache extends AbstractCache<String> {


    public void saveTodayApp(String date,  List<ApkDto> apkDtoList){
        String str=get(date);
        Assert.isTrue(apkDtoList.size()<=CodeConstant.TODAY_APP_LIST_SIZE,"今日主题不能超过4个App");

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
            Assert.isTrue(cacheApkList.size()<=CodeConstant.TODAY_APP_LIST_SIZE,"今日主题不能超过4个APP");
            put(date,JsonUtil.beanToJson(cacheApkList));
        }else{

            //否则就是当天第一次添加，直接保存
            put(date,JsonUtil.beanToJson(apkDtoList));
        }
    }


    public List<ApkDto> getApkList(String date){
        String str=get(date);
        if(StringUtil.isNil(str)){
            return null;
        }
        return JsonUtil.jsonToList(str,ApkDto.class);
    }


    public void delete(String date,String id){
        String str=get(date);
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
            put(date,JsonUtil.beanToJson(cacheApkList));
        }
    }

    @Override
    protected String getPrefix() {
        return "chain_store_today";
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
