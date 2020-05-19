package com.knowology.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>发送消息</p>
 *
 * @author : Conan
 * @date : 2019-08-01 15:20
 **/
@Component
public class OccsKafkaProducer {
    private final KafkaTemplate<?, String> kafkaTemplate;

    @Autowired
    public OccsKafkaProducer(KafkaTemplate<?, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息至kafka
     * @param topic
     * @param message
     */
    public void sendMessage(String topic,String message) {
        kafkaTemplate.send(topic,message);
    }
}
