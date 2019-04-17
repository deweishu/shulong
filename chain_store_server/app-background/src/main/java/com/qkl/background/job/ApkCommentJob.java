package com.qkl.background.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每天定时更新apk的评分
 * @author dengjihai
 * @create 2018-09-13
 **/
@Component
public class ApkCommentJob {


    private static final Logger logger = LoggerFactory.getLogger(ApkCommentJob.class);


    /***
     * 每天的凌晨1点进行更新apk的评分
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateAppComment() {

        logger.info("\n 定时任务  ===========>  更新应用评分开始");


    }
}
