package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/searchItem")
public class ItemSearchController {
    //timeout 设置调用服务超时时间，默认时间是1秒
    @Reference(timeout = 50000)
    private ItemSearchService itemSearchService;

    /**
     * 商品条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map searchMap){
        return  itemSearchService.search(searchMap);
    }


}
