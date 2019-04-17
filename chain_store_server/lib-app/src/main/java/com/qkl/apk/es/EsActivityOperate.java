package com.qkl.apk.es;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qkl.apk.bean.AcitivityType;
import com.qkl.common.constant.EsCodeConstant;
import com.qkl.common.page.Page;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-08-28
 **/
@Service
public class EsActivityOperate {

    private  final Logger logger = LoggerFactory.getLogger(EsActivityOperate.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private Client esClient;


    public void init() throws Exception {
        if (!elasticsearchTemplate.indexExists(EsCodeConstant.ACTIVITY_ES_INDEX)) {
            elasticsearchTemplate.createIndex(EsCodeConstant.ACTIVITY_ES_INDEX);
        }
        elasticsearchTemplate.putMapping(EsActivityInfo.class);
        initData();
    }

    /**
     * 初始化数据操作
     */
    private void initData(){

    }


    /**
     * 对活动信息进行存储到es
     * @param esActivityInfo
     * @return
     * @throws JsonProcessingException
     */
    public  Boolean inserOrUpdateActivity(EsActivityInfo esActivityInfo) throws Exception {
        BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();
        String json = JSON.toJSONString(esActivityInfo);
        logger.info("\n json:{}",json.toString());
        IndexRequest request = esClient.prepareIndex(EsCodeConstant.ACTIVITY_ES_INDEX,EsCodeConstant.ACTIVITY_ES_TYPE,esActivityInfo.getId()).setSource(json).request();
        bulkRequestBuilder.add(request);
        BulkResponse responses = bulkRequestBuilder.execute().actionGet();
        logger.info("\n ####　:{}",responses.hasFailures());
        if(responses.hasFailures()){
            logger.info("#####  insert/update fail,index:{},type:{} #####",EsCodeConstant.ACTIVITY_ES_INDEX,EsCodeConstant.ACTIVITY_ES_TYPE);
        }
        if (responses.hasFailures()) {
            logger.info("\n #############  更新 ESACTIVITY info 到es失败 #############   ");
            return  false;
        }else{
            logger.error("\n @@@@@@@@@@@@  更新 ESACTIVITY info 到es成功 @@@@@@@@@@@@@   ");
            return  true;
        }
    }
    /**
     * 通过活动类型查询出活动信息
     * @param acitivityType
     * @return
     */
    public List<EsActivityInfo> getActivityList(AcitivityType acitivityType) {
        SearchRequestBuilder srb = esClient.prepareSearch(EsCodeConstant.ACTIVITY_ES_INDEX)
                .setTypes(EsCodeConstant.ACTIVITY_ES_TYPE);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("type",acitivityType.getCode()));
        queryBuilder.must(QueryBuilders.termQuery("status",true));
        srb.setQuery(queryBuilder);
        srb.addSort("createDate", SortOrder.DESC);
        int from =0;
        srb.setFrom(from).setSize(100);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<EsActivityInfo> results = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            EsActivityInfo entity = JSON.parseObject(searchHit.getSourceAsString(),EsActivityInfo.class);
            results.add(entity);
        }
        logger.info(" ## es activity  list size: {}",results.size());
        return  results;
    }




}
