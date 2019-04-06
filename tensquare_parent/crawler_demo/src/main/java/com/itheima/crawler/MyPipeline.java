package com.itheima.crawler;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPipeline implements Pipeline {


    @Override
    //处理数据的方法
    public void process(ResultItems resultItems, Task task) {
        //获得title
        String title =  resultItems.get("title");

        System.out.println("这是自定义的MyPipeline,title=" + title);

        //调用Dao 把数据存到数据库

    }
}
