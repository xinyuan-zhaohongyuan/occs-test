package com.knowology.thread;

import com.knowology.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @ClassName AsyncTaskService
* @Description 异步外呼任务
* @author Conan  :  没有真相
* @Date 2019/4/7 15:45
* @version 1.0.0
*/
@Service
public class TaskAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(TaskAsyncService.class);
    @Value("${occs.example.url}")
    private String occs_example_url;
    @Async("asyncPoolTaskExecutor")
    public void callTask(Map<String,Object> params){
        logger.info("外呼调用请求参数为:"+params.toString());
        HttpClientUtil.postRequest(occs_example_url, params, 30000, 30000);
    }
}
