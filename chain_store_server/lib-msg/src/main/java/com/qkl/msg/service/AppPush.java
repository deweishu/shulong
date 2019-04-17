package com.qkl.msg.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.qkl.common.util.JsonUtil;
import com.qkl.msg.dto.ApkTestDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "AujyhdK83Q62bERijFwF25";
    private static String appKey = "Z5oeGTnvac6PZ0TXFpvOw9";
    private static String masterSecret = "arfIRXXdhg9uW5q8Pvztf7";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);


        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
//        LinkTemplate template = new LinkTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//        template.setTitle("欢迎使用ChainStore");
//        template.setText("这个是通知内容，你可以看到的。");
//        template.setUrl("http://getui.com");



        NotificationTemplate template =new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        String json="{\n" +
                "\t\"id\": \"2c92155c6660ef020166617674ed00bd\",\n" +
                "\t\"name\": \"先知\",\n" +
                "\t\"namePinyin\": \"xianzhi\",\n" +
                "\t\"logo\": \"https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/img/20181011/c256aeb882cf41709138b84fc15e8e89.png\",\n" +
                "\t\"mainImg\": \"https://chainstore.oss-cn-shenzhen.aliyuncs.com/chain_store/img/20181011/066e4c3f5fd74b868d0f5a0cf71dcea3.jpg\",\n" +
                "\t\"appDesc\": \"[先知]是一个专业的区块链信息分发和爆料平台，区块链行业热点资讯应有尽有，基于机器学习的个性化资讯推荐引擎，3秒算出你感兴趣的资讯，只为你推荐最\\r\\n  感兴趣的信息。\\r\\n  1、资讯推荐\\r\\n  推荐引擎为你推荐区块链热点资讯，为你提供行业最新动态，了解行业变化，为决策做参考。\\r\\n  2、匿名爆料\\r\\n  无数圈内人士在此敞开心扉交流，帮你洞悉圈内万象，关注爆料板块，能获悉内幕，也能了解业内八卦，从舆论导向看市场行情。\\r\\n  3、新闻闪讯\\r\\n  全天实时播报行业快讯，不用再担心错过任何热点，时时刻刻为你推荐你感兴趣的快讯，帮你掌握一手行情。\\r\\n  4、专业行情\\r\\n  覆盖几十所交易所，涉及到上万个交易对，全球行情走势可时时刻刻查看。\",\n" +
                "\t\"appSpecial\": \"实时金融信息爆料与信息分发平台\",\n" +
                "\t\"status\": 10,\n" +
                "\t\"categoryId\": \"5\",\n" +
                "\t\"createTime\": 1539233445000,\n" +
                "\t\"createDate\": \"2018-10-11\",\n" +
                "\t\"downNum\": 1,\n" +
                "\t\"candyNum\": 10,\n" +
                "\t\"haveAd\": false,\n" +
                "\t\"havePlugin\": false,\n" +
                "\t\"havePeople\": true,\n" +
                "\t\"clientType\": 30,\n" +
                "\t\"scoreNum\": 5,\n" +
                "\t\"packageName\": \"com.lian.xianzhi\",\n" +
                "\t\"appSort\": false,\n" +
                "\t\"appSortNum\": 1,\n" +
                "\t\"gameSort\": false,\n" +
                "\t\"gameSortNum\": 1,\n" +
                "\t\"buildId\": \"com.sandianzhong\",\n" +
                "\t\"plistFile\": null\n" +
                "}";


        ApkTestDto apkTestDto = JsonUtil.jsonToBean(json,ApkTestDto.class);

        template.setTransmissionContent(JsonUtil.beanToJson(apkTestDto));
        template.setTransmissionType(1);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("欢迎使用ChainStore-2018-10-22");
        style.setText("即可打开ChainStore-2018-10-22");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);



        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}
