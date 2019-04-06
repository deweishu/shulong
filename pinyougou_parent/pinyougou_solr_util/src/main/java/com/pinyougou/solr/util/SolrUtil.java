package com.pinyougou.solr.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 将满足条件商品数据导入索引库
     * 1、上架商品导入索引库  tb_goods  is_marketable='1'
     2、商品状态为1，正常状态 tb_item   status='1'
     */
    public void dataImport(){
        List<TbItem> itemList = itemMapper.findAllGrounding();
        //处理动态域字段赋值
        for (TbItem item : itemList) {
            String spec = item.getSpec();
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            item.setSpecMap(specMap);
        }

        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
    }


}
