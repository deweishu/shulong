package com.tensquare.crawler;

import com.tensquare.crawler.pipeline.ArticleTxtPipeline;
import com.tensquare.crawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class CrawlerTask {

    @Autowired
    private ArticleProcessor articleProcessor;

    @Autowired
    private ArticleTxtPipeline articleTxtPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    @Scheduled(cron = "05 57 15 * * ?")
    public void aiTask(){
        articleTxtPipeline.setChannelid("ai");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/ai").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }

    @Scheduled(cron = "40 02 16 * * ?")
    public void dbTask(){
        articleTxtPipeline.setChannelid("db");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/db").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }

    @Scheduled(cron = "50 07 16 * * ?")
    public void webTask(){
        articleTxtPipeline.setChannelid("web");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/web").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }




    //{秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    @Scheduled(cron = "20 42 16 * * ?")
    public void newsTask(){
        articleTxtPipeline.setChannelid("news");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/news").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }





    @Scheduled(cron = "55 46 16 * * ?")
    public void blockchainTask(){
        articleTxtPipeline.setChannelid("blockchain");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/blockchain").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }






    @Scheduled(cron = "0 48 16 * * ?")
    public void langTask(){
        articleTxtPipeline.setChannelid("lang");
        Spider.create(articleProcessor).
                addUrl("https://blog.csdn.net/nav/lang").
                addPipeline(articleTxtPipeline).
                setScheduler(redisScheduler).
                run();
    }
}
