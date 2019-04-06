package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.WexinPayService;
import com.pinyougou.pojo.TbPayLog;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class WexinPayController {

    @Reference(timeout = 500000)
    private WexinPayService wexinPayService;

    /**
     * 生成二维码
     */
    @RequestMapping("/createNative")
    public Map<String,Object> createNative() throws Exception {
        //根据用户名，获取用户支付信息
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbPayLog payLog= wexinPayService.queryPayInfo(userId);
        return wexinPayService.createNative(payLog.getOutTradeNo(),payLog.getTotalFee()+"");
    }

    /**
     * 查找支付状态
     *
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no){

        Result result=null;

        int count=1;
        try {
            while (true){
                //每3秒调用一次
                Thread.sleep(3000);

                //超过5分钟，调用超时
                count++;
                if(count>100){
                    return new Result(false,"timeout");
                }

                Map resultMap = wexinPayService.queryPayStatus(out_trade_no);

                //基于交易状态判断是否支付成功
                if("SUCCESS".equals(resultMap.get("trade_state"))){
                    //支付成功后，修改支付日志和订单表状态
                    wexinPayService.updatePayStatus(out_trade_no, (String) resultMap.get("transaction_id"));
                    return result= new Result(true,"支付成功");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"支付失败");
        }

    }









}
