package com.knowology.schedule;

import com.knowology.thread.TaskDataFilterAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用来处理第三方数据根据过滤规则筛选后同步至号码组
 *
 * @author xullent
 */
@ConditionalOnProperty(prefix = "occs.schedule.remote-datasource-filter-task",value = "enable",havingValue = "true")
@Component
public class RemoteDataSourceFilterTask {

    private static final Logger logger = LoggerFactory.getLogger(RemoteDataSourceFilterTask.class);
    @Autowired
    private TaskDataFilterAsyncService taskDataFilterAsyncService;

    /**
     * 执行筛选入库任务
     */
    @Scheduled(cron = "50 59 23 * * ?")
    public void execute(){

        logger.info("开始执行数据筛选入库");
        taskDataFilterAsyncService.dataFilter();
        logger.info("结束执行数据筛选入库");
    }
}
