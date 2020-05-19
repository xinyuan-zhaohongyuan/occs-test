package com.knowology.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


/**
 * @description:
 * @author: Conan
 * @create: 2019-03-11 16:51
 **/
@Service
public class Provider {

    @Autowired
    private JmsTemplate jmsTemplate;
    /**
     * 发送消息，estination是发送到的队列，message是待发送的消息
     * @param destination
     * @param message
     */
    public void sendMessage(Destination destination, String message) {
        System.out.println(jmsTemplate.getDeliveryMode());
        jmsTemplate.convertAndSend(destination, message);
    }
    /**
     * 发送消息，message是待发送的消息
     * @param message
     */
    public void sendMessage(String destinationName, String message) {
        jmsTemplate.convertAndSend(destinationName, message);
    }
}
