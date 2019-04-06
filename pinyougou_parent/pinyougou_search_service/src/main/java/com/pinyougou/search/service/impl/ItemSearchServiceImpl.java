package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {

        //高亮查询对象
        HighlightQuery query = new SimpleHighlightQuery();

        //1、关键字条件查询
        //{keywords:'华为'}
        String keywords = (String) searchMap.get("keywords");
        Criteria criteria = null;
        if(keywords!=null && !"".equals(keywords)){
            criteria = new Criteria("item_keywords").is(keywords);
        }else{
            //*:*
            criteria = new Criteria().expression("*:*");
        }

        //2、品牌过滤条件查询
        String brand = (String) searchMap.get("brand");
        if(brand!=null && !"".equals(brand)){
            //品牌条件对象
            Criteria brandCriteria = new Criteria("item_brand").is(brand);
            //过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery(brandCriteria);
            query.addFilterQuery(filterQuery);
        }

        //3、分类过滤条件查询
        String category = (String) searchMap.get("category");
        if(category!=null && !"".equals(category)){
            //条件对象
            Criteria categoryCriteria = new Criteria("item_category").is(category);
            //过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery(categoryCriteria);
            query.addFilterQuery(filterQuery);
        }

        //4、规格过滤条件查询
        Map<String,String> specMap= (Map<String,String>) searchMap.get("spec");
        if(specMap!=null){

            for(String key:specMap.keySet()){
                //条件对象
                Criteria specCriteria = new Criteria("item_spec_"+key).is(specMap.get(key));
                //过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery(specCriteria);
                query.addFilterQuery(filterQuery);
            }

        }

        //5、价格过滤条件查询
        String price = (String) searchMap.get("price");
        if(price!=null && !"".equals(price)){
            //切割价格区间
            String[] prices = price.split("-");

            //0-500  500-1000  1000-*  0  500  1000 *  主要判断临界值  0  *
            if(!"0".equals(prices[0])){
                //条件对象
                Criteria priceCriteria = new Criteria("item_price").greaterThan(prices[0]);
                //过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery(priceCriteria);
                query.addFilterQuery(filterQuery);
            }

            if(!"*".equals(prices[1])){
                //条件对象
                Criteria priceCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
                //过滤查询对象
                FilterQuery filterQuery = new SimpleFilterQuery(priceCriteria);
                query.addFilterQuery(filterQuery);
            }

        }

        //6、基于价格和时间排序查询
        String sortType = (String) searchMap.get("sort");
        String sortField = (String) searchMap.get("sortField");
        if(sortField!=null && !"".equals(sortField)){
            if ("ASC".equals(sortType)) {
                //排序条件
                Sort sort = new Sort(Sort.Direction.ASC,"item_"+sortField);
                query.addSort(sort);
            }else {
                //排序条件
                Sort sort = new Sort(Sort.Direction.DESC,"item_"+sortField);
                query.addSort(sort);
            }
        }

        //7、分页条件查询
        Integer pageNo = (Integer) searchMap.get("pageNo"); //1
        Integer pageSize = (Integer) searchMap.get("pageSize"); //60
        query.setOffset((pageNo-1)*pageSize);//分页查询起始值   0-59  60-119 120-179
        query.setRows(pageSize);//每页展示记录数


        //设置高亮
        //高亮条件对象
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");//添加高亮显示字段
        highlightOptions.setSimplePrefix("<font color='red'>");//高亮前缀
        highlightOptions.setSimplePostfix("</font>");//高亮前缀

        query.setHighlightOptions(highlightOptions);

        //条件查询设置
        query.addCriteria(criteria);

        //高亮条件查询
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);

        List<TbItem> itemList = page.getContent();
        for (TbItem item : itemList) {
            //处理高亮结果
            List<HighlightEntry.Highlight> highlights = page.getHighlights(item);
            if(highlights!=null && highlights.size()>0){
                //高亮实体对象
                HighlightEntry.Highlight highlight = highlights.get(0);

                List<String> snipplets = highlight.getSnipplets();
                //替换高亮内容
                if(snipplets!=null && snipplets.size()>0){
                    item.setTitle(snipplets.get(0));
                }
            }
        }

        Map<String ,Object> resultMap = new HashMap<>();
        //商品列表
        resultMap.put("rows",itemList);
        //返回当前和总页数
        int totalPages = page.getTotalPages();
        resultMap.put("pageNo",pageNo);
        resultMap.put("totalPages",totalPages);

        return resultMap;
    }
}
