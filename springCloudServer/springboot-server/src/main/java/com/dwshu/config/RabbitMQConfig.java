package com.dwshu.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


/**
 * @author dwshu
 * @create 2019/12/8
 */
@Configuration
public class RabbitMQConfig {
    //交换机-主题模式
    private static final String topicExchangeName = "topic-exchange";

    //交换机-分裂模式
    private static final String fanoutExchange = "fanout-exchange";

    //交换机-头模式
    private static final String headersExchange = "headers-exchange";

    //queueName 队列名称
    private static final String queueName = "myQueue";

    //申明队列
    // name: 消息队列名称
    // durable：是否持久化，默认true
    // exclusive: 该消息队列是否只是在当前的connection生效，默认false
    // autoDelete： 表示消息队列没有在使用时将被自动删除，默认false
    @Bean
    public Queue queue(){
        return new Queue("myQueue",false,true,true);
    }

    //申明topic交换机
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(topicExchangeName);
    }

    //将队列与Topic交换机进行绑定，并指定路由键
    @Bean
    public Binding topicBinding(Queue queue,TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("myQueue.#");
    }


    //申明fanout交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(fanoutExchange);
    }

    //将队列与fanout交换机进行绑定
    public Binding fanoutBinding(Queue queue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }


    //申明headers交换机
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(headersExchange);
    }

    //将队列与headersExchange交换机进行绑定
    @Bean
    public Binding headersBinding(Queue queue,HeadersExchange headersExchange){
        Map<String ,Object> map=new HashMap<>();
        map.put("First","A");
        map.put("Fourth","D");
        //whereAny表示部分匹配，whereAll表示全部匹配
        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();

    }
}
