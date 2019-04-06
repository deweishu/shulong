package com.tensquare.crawler.pipeline;

import com.tensquare.crawler.dao.ArticleDao;
import com.tensquare.crawler.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.HTMLUtil;
import util.IKUtil;
import util.IdWorker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Component
public class ArticleTxtPipeline implements Pipeline {


    //分词语料库的目录 F:\workspace\sk\sz42\ai\crawler
    @Value("${ai.dataDir}")
    private String dataDir;

    private String channelid;

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    @Override
    //处理数据的方法, 把数据存到数据库里面
    public void process(ResultItems resultItems, Task task) {
  
        try {
            //获得标题和内容
            String title = resultItems.get("title");
            String content = resultItems.get("content");
            //去掉内容的标签
            content = HTMLUtil.delHTMLTag(content);
            //进行分词, 再存到文件里面
            String splitStr = IKUtil.split(title + " " + content, " ");
            //把数据存到文件里面
            PrintWriter printWriter = new PrintWriter(new File(dataDir + "/" + channelid + "/" + UUID.randomUUID() + ".txt"));
            printWriter.print(splitStr);
            //释放资源
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
