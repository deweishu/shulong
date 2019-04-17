package com.qkl.background.mq;



import com.qkl.common.util.StringUtil;
import com.qkl.common.util.TimeUtil;
import com.qkl.mq.cache.MQExceptionCache;
import com.qkl.mq.dto.MQExceptionMonitorRuleDto;
import com.qkl.mq.sender.MQExceptionSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ所有队列监听消费者的统一父类
 * Created by linyh on 2017/8/25.
 */
public abstract class AbstractMQListener {
    private static Logger log = LoggerFactory.getLogger(AbstractMQListener.class);

    @Autowired
    private MQExceptionCache mqExceptionCache;

    private static long lastAlarm = 0L; //最后一次告警时间

    private MQExceptionMonitorRuleDto info;
    private String ruleId;
    private String queueName;
    private int threshold = 5;
    private String receiverName = "邓集海";
    private String receiverPhone = "13918702240";



    /**
     * 子类初始化时调用该方法
     */
    @PostConstruct
    public void init(){
        // 获取子类中的RabbitListener注解信息
        RabbitListener rl = AnnotationUtils.findAnnotation(getClass(), RabbitListener.class);
        if(rl != null) {
            this.queueName = rl.queues()[0];

        }
    }

    @RabbitHandler
    protected void process(String msg){
        log.info("收到MQ消息："+msg);
        try {
            doProcess(msg);
        }catch (RuntimeException e){

            log.error(queueName + " 发生异常：" + e);
            String number = mqExceptionCache.get(queueName);
            if(StringUtil.isNil(number)){
                mqExceptionCache.increment(queueName);
            }else{
                int no = Integer.valueOf(number);
                if(info != null && no > info.getThreshold()){
                    log.info(queueName + " 异常次数超过阀值，不再抛出");
                    sendToExceptionQueue(msg);
                    if(TimeUtil.now().getTime() - lastAlarm > (5 * 60 * 1000)) { //5分钟内不在重复发送
                        persistent();
                        sendSms();
                        lastAlarm = TimeUtil.now().getTime();
                    }
                    mqExceptionCache.remove(queueName);
                    return;
                }else{
                    log.info(queueName + "异常次数未超过阀值，抛出异常，消息消费未成功");
                    mqExceptionCache.increment(queueName);
                }
            }
            throw e;
        }
    }

    /**
     * 发送到异常队列
     */
    private void sendToExceptionQueue(String message){
        MQExceptionSender.sendToMQException(message);
    }

    /**
     * 持久化
     */
    private void persistent(){

    }

    /**
     * 发送短信
     */
    private void sendSms(){
        log.info(queueName + " 队列消费的异常次数超过阀值, 短信发送....");
        Map<String,String> params = new HashMap();
        params.put("name",this.receiverName);
        params.put("queueName",this.queueName);
    }

    protected abstract void doProcess(String msg);
}
