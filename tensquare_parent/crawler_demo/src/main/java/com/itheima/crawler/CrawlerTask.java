package com.itheima.crawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 爬虫启动类(任务类)
 */
public class CrawlerTask {

    public static void main(String[] args) {
        Spider.
                create(new MyPageProcessor()).  //设置页面解析器
                addUrl("https://www.csdn.net/nav/ai"). //设置需要爬取的页面的路径
                //addPipeline(new ConsolePipeline()). //设置控制台的管道
                        addPipeline(new FilePipeline("F:\\workspace\\sk\\sz42\\ai\\crawler")). //设置文件的管道(把爬取的内容存到文件里面)
                addPipeline(new JsonFilePipeline("F:\\workspace\\sk\\sz42\\ai\\crawler")). //设置json文件的管道(把爬取的内容存到文件里面 json格式)
                addPipeline(new MyPipeline()). //设置自定义的管道
                //setScheduler(new QueueScheduler()). //设置内存队列(jdk)
                // setScheduler(new FileCacheQueueScheduler("F:\\workspace\\sk\\sz42\\ai\\crawler")). //设置文件队列
                setScheduler(new RedisScheduler("127.0.0.1")). //设置redis队列
                run(); //启动

    }

}
