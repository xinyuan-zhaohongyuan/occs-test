package com.knowology;

import com.knowology.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @description:
 * @author: Conan
 * @create: 2019-03-06 14:17
 **/
@MapperScan("com.knowology.**.dao")
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@EnableConfigurationProperties(SecurityProperties.class)
@SpringBootApplication
public class OccsWebApplication {
	public static void main(String[] args) {
		try {
			SpringApplication.run(OccsWebApplication.class,args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
