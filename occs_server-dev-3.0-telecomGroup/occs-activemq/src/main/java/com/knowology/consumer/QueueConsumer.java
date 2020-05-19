package com.knowology.consumer;

import com.knowology.entity.ActiveMQContaint;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @description:
 * @author: Conan
 * @create: 2019-03-11 16:45
 **/

public abstract class QueueConsumer {
    /*** 
    * @Description: 队列监听器 
    * @Param: [message, session] 
    * @return: void 
    * @Author: Conan 
    * @Date: 2019/3/12 
    */ 
    @JmsListener(destination = ActiveMQContaint.QUEUE_TALK, containerFactory = "jmsListenerContainerFactory")
    public void receive(TextMessage message, Session session){

        try {
            deal(message);
            message.acknowledge();
        } catch (Exception e) {
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }

        }

    }
    /*** 
    * @Description: 子类实现抽象方法，执行业务逻辑 
    * @Param: [] 
    * @return: void 
    * @Author: Conan 
    * @Date: 2019/3/12 
    */
    public abstract void deal(TextMessage message);
}
