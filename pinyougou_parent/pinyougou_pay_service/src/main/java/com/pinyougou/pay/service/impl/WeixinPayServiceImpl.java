package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.pay.service.WexinPayService;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbPayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import util.HttpClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class WeixinPayServiceImpl implements WexinPayService {

    @Value("${appid}")
    private String appid;//公众号Id

    @Value("${partner}")
    private String partner;//商户号ID

    @Value("${partnerkey}")
    private String partnerkey;//商户秘钥

    @Value("${notifyurl}")
    private String notifyurl;//回调通知地址



    @Override
    public Map<String, Object> createNative(String out_trade_no, String total_fee) throws Exception {

        //1组成调用接口参数
        Map<String,String> paramMap= new HashMap<>();
        paramMap.put("appid",appid);
        paramMap.put("mch_id",partner);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body","品优购");
        paramMap.put("out_trade_no",out_trade_no);
        paramMap.put("total_fee",total_fee);//支付金额，单位是分
        paramMap.put("spbill_create_ip","127.0.0.1");
        paramMap.put("notify_url",notifyurl);
        paramMap.put("trade_type","NATIVE");
        paramMap.put("product_id","1");

        //将map转为xml
        String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);
        System.out.println(paramXml);
        //2基于httpclient发起请求
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setHttps(true);
        httpClient.setXmlParam(paramXml);
        httpClient.post();
        //3处理请求响应结果
        String resultXml = httpClient.getContent();
        System.out.println(resultXml);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);

        Map<String,Object> map = new HashMap<>();
        map.put("code_url",resultMap.get("code_url"));
        map.put("out_trade_no",out_trade_no);
        map.put("total_fee",total_fee);

        return map;
    }

    @Override
    public Map queryPayStatus(String out_trade_no) throws Exception {
        //1组成调用接口参数
        Map<String,String> paramMap= new HashMap<>();
        paramMap.put("appid",appid);
        paramMap.put("mch_id",partner);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("out_trade_no",out_trade_no);

        //将map转为xml
        String paramXml = WXPayUtil.generateSignedXml(paramMap, partnerkey);
        System.out.println(paramXml);
        //2基于httpclient发起请求
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setHttps(true);
        httpClient.setXmlParam(paramXml);
        httpClient.post();

        //3处理请求响应结果
        String resultXml = httpClient.getContent();
        System.out.println(resultXml);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
        return resultMap;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbPayLogMapper payLogMapper;

    @Autowired
    private TbOrderMapper orderMapper;

    @Override
    public TbPayLog queryPayInfo(String userId) {
        return (TbPayLog) redisTemplate.boundHashOps("payLog").get(userId);
    }

    @Override
    public void updatePayStatus(String out_trade_no,String transaction_id) {
        TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);

        //更新支付日志状态
        payLog.setTradeState("2");//已支付
        payLog.setPayTime(new Date());
        payLog.setTransactionId(transaction_id);
        payLogMapper.updateByPrimaryKey(payLog);

        //更新订单状态
        String orderList = payLog.getOrderList();
        String[] orderIds = orderList.split(",");
        for (String orderId : orderIds) {
            TbOrder order = orderMapper.selectByPrimaryKey(Long.parseLong(orderId));
            order.setPaymentTime(new Date());
            order.setStatus("2");//已支付
            orderMapper.updateByPrimaryKey(order);
        }

        //清空redis中记录的支付日志
        redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());
    }
}
