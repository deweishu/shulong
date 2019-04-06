package com.pinyougou.page.listener;

import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import groupEntity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddItemPageMessageListener implements MessageListener {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage=(TextMessage)message;
            String goodsId = textMessage.getText();

            //    第1步：创建一个 Configuration 对象
            Configuration configuration = freeMarkerConfig.getConfiguration();
            //    第2步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate("item.ftl");
            //    第3步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
            Goods goods = itemPageService.findOne(Long.parseLong(goodsId));
            List<TbItem> itemList = goods.getItemList();
            Writer out =null;
            for (TbItem item : itemList) {
                Map<String,Object> map = new HashMap<>();
                map.put("goods",goods);
                map.put("item",item);
                //    第4步：创建一个 Writer 对象，一般创建一 FileWriter 对象，指定生成的文件名。
                out = new FileWriter("f:/item42/"+item.getId()+".html");
                //    第5步：调用模板对象的 process 方法输出文件。
                template.process(map,out);
                //    第6步：关闭流
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
