package com.qkl.common.mq;

import com.qkl.common.util.JsonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract  class MQSender {

    private static RabbitTemplate rabbitTemplate;

	@Autowired
	void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		MQSender.rabbitTemplate = rabbitTemplate;
	}

	protected static void send(String routingKey, Object message) {
		rabbitTemplate.convertAndSend(routingKey, message);
	}

	protected static void sendByJson(String routingKey, Object message) {
		rabbitTemplate.convertAndSend(routingKey, JsonUtil.beanToJson(message));
	}

	protected static void send(String exchange, String routingKey, Object message){
		rabbitTemplate.convertAndSend(exchange, routingKey, JsonUtil.beanToJson(message));
	}

	/**
	 *  发送延迟消息
	 * @param routingKey
	 * @param message
	 * @param timeout 消息有效期
	 */
	protected static void send(String routingKey, Object message, long timeout){
		MessageConverter mc = rabbitTemplate.getMessageConverter();
		MessageProperties mp = new MessageProperties();
		mp.setExpiration(String.valueOf(timeout));
		Message m = mc.toMessage(JsonUtil.beanToJson(message), mp);
		rabbitTemplate.send(routingKey, m);
	}

}
