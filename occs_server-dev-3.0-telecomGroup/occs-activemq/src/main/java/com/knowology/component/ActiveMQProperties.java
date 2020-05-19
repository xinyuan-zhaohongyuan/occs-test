package com.knowology.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 加载ActiveMQ参数
 * @author: Conan
 * @create: 2019-03-11 11:14
 **/
@Component
@ConfigurationProperties("activemq")
@Data
public class ActiveMQProperties {
    private String brokerUrl;
    private String username;
    private String password;

}
