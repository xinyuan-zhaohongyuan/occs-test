package com.knowology.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @description: ActiveMQ配置类
 * @author: Conan
 * @create: 2019-03-11 11:07
 **/
@Configuration
public class ActiveMQConfig {

    /**
     * 消息重发
     */
    public RedeliveryPolicy newRedeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);//指数倍数递增方式增加延迟时间
        redeliveryPolicy.setMaximumRedeliveries(2);//最大传送次数
        redeliveryPolicy.setInitialRedeliveryDelay(1000L);//初始重发延迟时间，默认1000L
        redeliveryPolicy.setBackOffMultiplier(2);//重连时间间隔递增倍数
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);//最大传送延迟
        return redeliveryPolicy;
    }

    /**
     * 收发消息
     * @param factory
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(
                    ActiveMQConnectionFactory factory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDeliveryPersistent(true);//持久化
        jmsTemplate.setConnectionFactory(factory);
        jmsTemplate.setDeliveryMode(4);
        return jmsTemplate;
    }

    /**
     * 重试
     * @return
     */
    @Bean("jmsBackOff")
    public BackOff backOff(){
        FixedBackOff backOff = new FixedBackOff();
        backOff.setInterval(5000);
        backOff.setMaxAttempts(2);
        return backOff;
    }

    /**
     * 监听工厂
     * @param singleConnectionFactory
     * @param backOff
     * @return
     */
    @Bean("jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
                    ActiveMQConnectionFactory singleConnectionFactory,
            @Qualifier("jmsBackOff")
                    BackOff backOff){

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        singleConnectionFactory.setRedeliveryPolicy(newRedeliveryPolicy());
        factory.setConnectionFactory(singleConnectionFactory);
        factory.setConcurrency("5-8");//设置连接数
        factory.setRecoveryInterval(5000L);//重连间隔时间
        factory.setSessionAcknowledgeMode(4);
        factory.setBackOff(backOff);//不能在brokerUrl里配置maxReconnectAttempts,否则这里设置的重连次数不生效，而且maxReconnectAttempts的也不生效
        return factory;
    }
}
