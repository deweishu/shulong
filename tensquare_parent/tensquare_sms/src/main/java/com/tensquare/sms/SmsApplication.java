package com.tensquare.sms;


import com.tensquare.sms.utils.SmsUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class,args);
    }


    @Bean
    public SmsUtil smsUtil(){
        return  new SmsUtil();
    }
}
