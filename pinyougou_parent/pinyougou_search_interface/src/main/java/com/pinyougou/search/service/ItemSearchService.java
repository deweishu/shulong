package com.pinyougou.search.service;

import java.util.Map;

public interface ItemSearchService {

    /**
     * 基于solr实现商品搜索  Map
     *
     */
    public Map<String,Object> search(Map searchMap );
}
