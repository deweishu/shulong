package com.dwshu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dwshu
 * @create 2019/11/12
 */

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String sayHello() {
        return restTemplate.getForEntity("http://eureka-server-provider/hello/",String.class).getBody();
    }
}
