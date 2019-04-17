package com.qkl.common.mq;


public class MQConstant {

    public static final String USER = "user";

    /**
     * 测试消息
     */
    public static final String TEST_MESSAGE = "test.message";

    public static final String TOPIC_MESSAGE = "topic.message";
    public static final String TOPIC_MESSAGES = "topic.messages";
    public static final String TOPIC_ALL = "topic.#";

    public static final String EXCHANGE = "exchange";
    public static final String FANOUTEXCHANGE = "fanoutExchange";

    /*  上面皆为MQ测试使用 */



    /**
     * 短信发送
     */
    public static final String QUEUE_SMS_SEND = "queue.sms.send";


    /**
     * mq异常队列
     */
    public static final String MQ_EXCEPTION = "mq.exception";


    /* es 消息队列 */

    /**
     * es数据send
     */
    public static final String QUEUE_ES_SEND = "queue.es.send";

    /**
     * 自动退款
     */
    public static final String AUTO_REFUND_ORDER_READY = "auto.refund.order.ready";
    public static final String AUTO_REFUND_ORDER_PROCESS = "auto.refund.order.process";

    /*用户注册监听*/
    public static final String USER_REGITER = "user_register";

    /**用户消息监听*/
    public static final String USER_MSG_REGISTER = "user_msg_register";

}
