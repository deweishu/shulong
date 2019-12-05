package com.dwshu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dwshu
 * @create 2019/11/9
 */
@RestController
public class ProviderController {

    @Autowired
    private Registration registration;

    @GetMapping("/info")
    public Object info(){
       return registration.getServiceId()+":"+registration.getHost()+":"+registration.getPort();
    }


    @GetMapping("/hello")
    public String sayHello(){

        return "hello spring cloud！";
    }
}
