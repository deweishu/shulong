package com.tensquare.crawler.pipeline;

import com.tensquare.crawler.dao.ArticleDao;
import com.tensquare.crawler.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.IdWorker;

@Component
public class ArticlePipeline implements Pipeline {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    private String channelid;

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    @Override
    //处理数据的方法, 把数据存到数据库里面
    public void process(ResultItems resultItems, Task task) {
        //获得标题和内容
        String title = resultItems.get("title");
        String content = resultItems.get("content");

        //封装成Article对象, 调用Dao存到数据库里面
        Article article = new Article();
        article.setId(idWorker.nextId()+"");
        article.setTitle(title);
        article.setContent(content);
        article.setChannelid(channelid);

        articleDao.save(article);


    }
}
