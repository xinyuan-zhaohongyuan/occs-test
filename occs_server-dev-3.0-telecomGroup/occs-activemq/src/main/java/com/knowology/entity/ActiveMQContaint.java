package com.knowology.entity;

/**
 * @description:
 * @author: Conan
 * @create: 2019-03-11 17:39
 **/
public class ActiveMQContaint {
    /**
     * 外呼演示的显示对话消息队列名
     */
    public static final String QUEUE_TALK = "QUEUE_TALK";

    /**
     * 外呼任务消息队列名
     */
    public static final String QUEUE_CALL = "QUEUE_CALL";

    /**
     * 发送短信消息队列名
     */
    public static final String QUEUE_SMS = "QUEUE_SMS";

    /**
     * 接收短信消息队列名
     */
    public static final String QUEUE_SMS_RESULT = "QUEUE_SMS_RESULT";

    /**
     * 接收外呼通话结果消息队列名
     */
    public static final String QUEUE_CALLRESULT = "QUEUE_CALLRESULT";

    /**
     * 发送场景测试消息队列名
     */
    public static final String QUEUE_EXAMPLE = "QUEUE_EXAMPLE";

}
