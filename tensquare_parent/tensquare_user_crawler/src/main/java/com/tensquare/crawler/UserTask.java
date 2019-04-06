package com.tensquare.crawler;

import com.tensquare.crawler.pipeline.UserPipeline;
import com.tensquare.crawler.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class UserTask {


    @Autowired
    private UserProcessor userProcessor;

    @Autowired
    private UserPipeline userPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    //{秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    @Scheduled(cron = "10 12 17 * * ?")
    public void userTask(){
        Spider.create(userProcessor).
                addUrl("https://www.csdn.net/nav/ai").
                addPipeline(userPipeline).
                setScheduler(redisScheduler).
                run();
    }


}
