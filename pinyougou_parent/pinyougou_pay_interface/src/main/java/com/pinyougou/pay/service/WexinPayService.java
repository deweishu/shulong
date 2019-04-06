package com.pinyougou.pay.service;

import com.pinyougou.pojo.TbPayLog;

import java.util.Map;

public interface WexinPayService {

    /**
     * 基于微信官方统一下单接口生成二维码
     */
    public Map<String,Object> createNative(String out_trade_no,String total_fee) throws Exception;

    /**
     * 查询微信支付状态
     *
     */
    public Map queryPayStatus(String out_trade_no) throws Exception;

    TbPayLog queryPayInfo(String userId);

    void updatePayStatus(String out_trade_no,String transaction_id);
}
