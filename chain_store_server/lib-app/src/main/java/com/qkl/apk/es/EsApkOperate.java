package com.qkl.apk.es;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.dto.ApkSearchReq;
import com.qkl.common.constant.EsCodeConstant;
import com.qkl.common.dto.PageResponse;
import com.qkl.common.page.Page;
import com.qkl.common.util.StringUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 */
@Service
public class EsApkOperate {

    private  final Logger logger = LoggerFactory.getLogger(EsActivityOperate.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private Client esClient;


    public void init() throws Exception {
        if (!elasticsearchTemplate.indexExists(EsCodeConstant.APK_ES_INDEX)) {
            elasticsearchTemplate.createIndex(EsCodeConstant.APK_ES_INDEX);
        }
        elasticsearchTemplate.putMapping(EsApk.class);
        initData();
    }


    /**
     * 初始化数据操作
     */
    private void initData(){

    }


    /**
     * 对apk信息进行存储到es
     * @param esApk
     * @return
     * @throws JsonProcessingException
     */
    public  Boolean inserOrUpdateApk(EsApk esApk) throws Exception {
        BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();
        String json = JSON.toJSONString(esApk);
        logger.info("\n json:{}",json.toString());
        IndexRequest request = esClient.prepareIndex(EsCodeConstant.APK_ES_INDEX,EsCodeConstant.APK_ES_TYPE,esApk.getId()).setSource(json).request();
        bulkRequestBuilder.add(request);
        BulkResponse responses = bulkRequestBuilder.execute().actionGet();
        logger.info("\n ####　:{}",responses.hasFailures());
        if(responses.hasFailures()){
            logger.info("#####  insert/update fail,index:{},type:{} #####",EsCodeConstant.APK_ES_INDEX,EsCodeConstant.APK_ES_TYPE);
        }
        if (responses.hasFailures()) {
            logger.error("\n #############  更新 esapk info 到es失败 #############   ");
            return  false;
        }else{
            logger.info("\n @@@@@@@@@@@@  更新 esapk info 到es成功 @@@@@@@@@@@@@   ");
            return  true;
        }
    }


    /**
     * 通过日期查询出最近3条的数据，首页用到
     * @param date
     * @return
     */
    public List<EsApk> getApkByDate(String date){
        SearchRequestBuilder srb = esClient.prepareSearch(EsCodeConstant.APK_ES_INDEX)
                .setTypes(EsCodeConstant.APK_ES_TYPE);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if(StringUtil.isNotNil(date)){
            queryBuilder.must(QueryBuilders.termQuery("createDate", date));
        }
        srb.setQuery(queryBuilder);
        srb.addSort("createTime", SortOrder.DESC);
        srb.setFrom(0).setSize(Page.INDEX_PAGE_SIZE);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();

        List<EsApk> results = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            EsApk entity = JSON.parseObject(searchHit.getSourceAsString(),EsApk.class);
            results.add(entity);
        }
        return results;
    }


    public EsApk getApkById(String apkId) throws ExecutionException, InterruptedException {
        GetResponse getResponse = esClient.prepareGet(EsCodeConstant.APK_ES_INDEX,EsCodeConstant.APK_ES_TYPE,apkId).execute().get();
        if(StringUtil.isNotNil(getResponse.getSourceAsString())){
            EsApk esArticle=JSON.parseObject(getResponse.getSourceAsString(),EsApk.class);
            return esArticle;
        }
        return null;
    }




    /**
     * 通过分类id，根据不同的字段排序 ，分页查询
     * @param apkSearchReq
     * @return
     */
    public PageResponse getApkList(ApkSearchReq apkSearchReq) {
        SearchRequestBuilder srb = esClient.prepareSearch(EsCodeConstant.APK_ES_INDEX)
                .setTypes(EsCodeConstant.APK_ES_TYPE);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if(StringUtil.isNotNil(apkSearchReq.getAppName())){


            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name",apkSearchReq.getAppName());
            if(StringUtil.isEnglish(apkSearchReq.getAppName())){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("namePinyin","*"+apkSearchReq.getAppName().toLowerCase()+"*");
                logger.info("\n ### ############### 拼音搜索++++++++++++++++++++++++");
                queryBuilder.must(wildcardQueryBuilder);
            }else{
                logger.info("\n ### 中文搜索++++++++++++++++++++++++");
                queryBuilder.must(matchQueryBuilder);
            }
        }
        if(StringUtil.isNotNil(apkSearchReq.getCategoryId())){
            queryBuilder.must(QueryBuilders.termQuery("categoryId",apkSearchReq.getCategoryId()));
        }
        //如果排序是应用榜
        if(apkSearchReq.getSortType()!=null && apkSearchReq.getSortType().equals(30)){
            queryBuilder.must(QueryBuilders.termQuery("appSort",true));
        }
        //如果排序是游戏榜
        if(apkSearchReq.getSortType()!=null && apkSearchReq.getSortType().equals(40)){
            queryBuilder.must(QueryBuilders.termQuery("gameSort", true));
        }
        //只查询上架中的
        queryBuilder.must(QueryBuilders.termQuery("status", ApkStatus.NORMAL.getCode()));
        srb.setQuery(queryBuilder);
        if(apkSearchReq.getSortType()!=null){
            if(apkSearchReq.getSortType().equals(10)){
                srb.addSort("createTime", SortOrder.DESC);
            }else if(apkSearchReq.getSortType().equals(20)){
                srb.addSort("downNum", SortOrder.DESC);
            }else if(apkSearchReq.getSortType().equals(30)){
                srb.addSort("appSortNum", SortOrder.ASC);
            }else if(apkSearchReq.getSortType().equals(40)){
                srb.addSort("gameSortNum", SortOrder.ASC);
            }
        }else{
            if(StringUtil.isNotNil(apkSearchReq.getCategoryId())){
                srb.addSort("updateTime", SortOrder.DESC);
            }else{
                srb.addSort(new ScoreSortBuilder().order(SortOrder.DESC));
            }
//            srb.addSort("createTime", SortOrder.DESC);
        }
        int from =(apkSearchReq.getPageNo()-1)* Page.PAGE_SIZE;
        srb.setFrom(from).setSize(Page.PAGE_SIZE);
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<EsApk> results = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            EsApk entity = JSON.parseObject(searchHit.getSourceAsString(),EsApk.class);
            results.add(entity);
        }
        logger.info(" ## es apk  list size: {}",results.size());
        PageResponse pageResponse = new PageResponse();
        pageResponse.setData(results);
        pageResponse.setCurrentPage(apkSearchReq.getPageNo());
        pageResponse.setShowCount(Page.PAGE_SIZE);
        pageResponse.setTotalResult((int) hits.getTotalHits());
        return  pageResponse;
    }
}
