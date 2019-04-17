package com.qkl.common.mq;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 一些通用的对象配置
 */
@Configuration
public class MQConfiguration {

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MQConstant.EXCHANGE);
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(MQConstant.FANOUTEXCHANGE);
    }


}
