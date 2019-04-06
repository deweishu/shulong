package com.tensquare.crawler.processor;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 页面解析类
 */

@Component
public class UserProcessor implements PageProcessor {

    @Override
    //处理分析页面, 参数page: 代表整个页面
    public void process(Page page) {
        //添加目标页面(以主页面作为种子路径. 进行添加)
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-zA-Z0-9_]+/article/details/[0-9]{8}").all());

        //解析获得了nickname
        String nickName = page.getHtml().xpath("//*[@id=\"uid\"]/text()").toString();
        //解析图片路径 https://avatar.csdn.net/C/4/6/3_qq_36698956.jpg
        String imgUrl =  page.getHtml().xpath("//*[@id=\"asideProfile\"]/div[1]/div[1]/a").css("img","src").toString();

        //判断是否有
        if(nickName != null && imgUrl != null){
            //put到filed里面
            page.putField("nickName",nickName);
            page.putField("imgUrl",imgUrl);
        }else{
            //忽略
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
