package com.pinyougou.page.listener;

import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbItem;
import groupEntity.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.util.List;

public class DeleItemPageMessageListener implements MessageListener{

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage=(TextMessage)message;
            String goodsId = textMessage.getText();

            //    第3步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
            Goods goods = itemPageService.findOne(Long.parseLong(goodsId));
            List<TbItem> itemList = goods.getItemList();

            for (TbItem item : itemList) {
                //删除静态页
                new File("f:/item42/"+item.getId()+".html").delete();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
