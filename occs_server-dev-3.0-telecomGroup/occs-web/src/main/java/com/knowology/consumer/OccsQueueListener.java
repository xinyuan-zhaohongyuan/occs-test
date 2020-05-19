package com.knowology.consumer;

import com.alibaba.fastjson.JSONObject;
import com.knowology.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @description:    监听器子类，实现deal方法，根据消息处理业务逻辑
 * @author: Conan
 * @create: 2019-03-12 16:31
 **/
@Component
public class OccsQueueListener extends QueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OccsQueueListener.class);
    @Override
    public void deal(TextMessage message) {
        try {
            String data = message.getText();
            if (data != null) {
                JSONObject jsonObject = JSONObject.parseObject(data);
                logger.info("接收通话内容:"+jsonObject);
                WebSocket.pushInfo(message.getText(),jsonObject.getString("sid"));
            }
        } catch (JMSException e) {
            logger.error("OccsQueueListener处理消息异常",e);
        }
    }
}
