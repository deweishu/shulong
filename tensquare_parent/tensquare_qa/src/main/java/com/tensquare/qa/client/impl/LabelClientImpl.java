package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        System.out.println("基础微服务不可用,熔断器触发了...");
        return new Result(false, StatusCode.REMOTEERROR,"基础微服务不可用,熔断器触发了");
    }
}
