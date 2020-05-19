package com.knowology.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @description 对SchedulerFactoryBean的拓展
 * @author Conan
 * @date 2019-03-29 15:04
 * @see org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration
 **/
@Configuration
public class OccsQuartzConfig {

    /**
     * 单数据源的时候，DataSource会被自动注入
     * @see org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.JdbcStoreTypeConfiguration
     * 1)以更具Bean风格的方式为Scheduler提供配置信息；
     * 2)让Scheduler和Spring容器的生命周期建立关联，相生相息；
     * 3)通过属性配置部分或全部代替Quartz自身的配置文件。
     */
    @Bean
    public SchedulerFactoryBeanCustomizer custSchedulerFactoryBean() {
        return schedulerFactoryBean -> {
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setAutoStartup(true);//SchedulerFactoryBean在初始化后是否马上启动Scheduler，默认为true。如果设置为false，需要手工启动Scheduler；
            schedulerFactoryBean.setStartupDelay(10);//在SchedulerFactoryBean初始化完成后，延迟多少秒启动Scheduler，默认为0，表示马上启动
            schedulerFactoryBean.setQuartzProperties(initProperties());//类型为Properties，允许你在Spring中定义Quartz的属性。
        };
    }

    private Properties initProperties() {
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "MyScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "50");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 集群配置
        prop.put("org.quartz.jobStore.isClustered", "false");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        return prop;
    }

}
