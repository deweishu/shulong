package com.itheima.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 页面解析类
 */
public class MyPageProcessor implements PageProcessor {

    @Override
    //处理分析页面, 参数page: 代表整个页面
    public void process(Page page) {
        //添加目标页面(以主页面作为种子路径. 进行添加)
        //https://blog.csdn.net/InFiNiTeemo/article/details/84931024
        //https://blog.csdn.net/qq_42562071/article/details/84716138
        //https://blog.csdn.net/weixin_37352167/article/details/84894733
        page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a-zA-Z0-9_]+/article/details/[0-9]{8}").all());
        //xpath(): 对内容进行过滤(说白了就是爬取到自己想到的内容)
        String title = page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").toString();
        //put到field里面
        page.putField("title",title);

    }


    @Override
    //配置的方法(失败之后重试的次数, 页面之间爬取时间间隔);
    public Site getSite() {
        Site site = new Site();
        //失败之后重试的次数,页面之间爬取时间间隔
        site.setRetryTimes(3).setSleepTime(100);
        return site;
    }
}
