package com.qkl.admin.service;

import com.qkl.admin.cache.HotSearchCache;
import com.qkl.admin.dto.HotSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotSearchService {


    @Autowired
    HotSearchCache hotSearchCache;


    /**
     * 添加热搜关键词
     * @param searchKey
     */
    public void save(String searchKey){
        List<String> stringList=hotSearchCache.getHotSearchList();
        if(stringList!=null){
            Assert.isTrue(stringList.size()>=10==false,"热搜关键词不能超过10个");
        }
        hotSearchCache.save(searchKey);
    }

    /**
     * 获取热搜关键词
     * @return
     */
    public List<HotSearchDto> getHotSearchKeyList(){
        List<String> hotSearchList=hotSearchCache.getHotSearchList();
        List<HotSearchDto> hotSearchDtoList = new ArrayList<>();
        if(hotSearchList!=null){
            hotSearchList.forEach(s -> {
                HotSearchDto hotSearchDto = new HotSearchDto();
                hotSearchDto.setName(s);
                hotSearchDtoList.add(hotSearchDto);
            });
        }
        return hotSearchDtoList;
    }


    public void delete(String searchKey){
        hotSearchCache.delete(searchKey);
    }

}
