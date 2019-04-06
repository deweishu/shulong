package com.tensquare.crawler.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 页面解析类
 */

@Component
public class ArticleProcessor implements PageProcessor {

    @Override
    //处理分析页面, 参数page: 代表整个页面
    public void process(Page page) {
        //添加目标页面(以主页面作为种子路径. 进行添加)
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-zA-Z0-9_]+/article/details/[0-9]{8}").all());
        //解析获得了title
        String title = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").toString();
        //解析获得内容
        String content =  page.getHtml().xpath("//*[@id=\"article_content\"]/div[2]").toString();

        if(title != null && content != null){
            //put到field里面
            page.putField("title",title);
            page.putField("content",content);
        }else{
            //跳过
            page.setSkip(true);
        }


    }


    @Override
    //配置的方法(失败之后重试的次数, 页面之间爬取时间间隔);
    public Site getSite() {
        Site site = new Site();
        //失败之后重试的次数,页面之间爬取时间间隔
        site.setRetryTimes(3).setSleepTime(500);
        return site;
    }
}
