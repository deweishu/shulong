package com.tensquare.crawler.pipeline;

import com.tensquare.crawler.dao.UserDao;
import com.tensquare.crawler.pojo.User;
import com.tensquare.crawler.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.IdWorker;

import java.io.IOException;

/**
 * 数据入库类
 */

@Component
public class UserPipeline implements Pipeline {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;


    @Override
    public void process(ResultItems resultItems, Task task) {

        try {
            //获得nickname和图片路径 https://avatar.csdn.net/C/4/6/3_qq_36698956.jpg
            String nickName = resultItems.get("nickName");
            String imgUrl = resultItems.get("imgUrl");

            //封装成User 存到数据库
            User user = new User();
            user.setId(idWorker.nextId() + "");
            user.setNickname(nickName);
            int index = imgUrl.lastIndexOf("/");
            String avatar = imgUrl.substring(index + 1);
            user.setAvatar(avatar);
            userDao.save(user);

            //保存图片到磁盘
            DownloadUtil.download(imgUrl,avatar,"F:\\workspace\\sk\\sz42\\ai\\crawler");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
