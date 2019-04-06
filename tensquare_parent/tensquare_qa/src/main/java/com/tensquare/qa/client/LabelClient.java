package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//调用的微服务的名字(就是注册到eureka注册中心的名字)
@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {
    //根据id查询标签(方法需要和tensquare-base名字的方法名一致, 路径需要加上类上面的@RequestMapping)
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    Result findById(@PathVariable(value = "labelId") String labelId);

}
