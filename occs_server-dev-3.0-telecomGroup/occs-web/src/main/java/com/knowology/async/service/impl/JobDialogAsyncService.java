package com.knowology.async.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.JobDialogMapper;
import com.knowology.model.JobDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通话内容分段入库
 * @author xullent
 * @date 2020/2/17 10:22
 */
@Service
public class JobDialogAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(JobDialogAsyncService.class);
    @Resource
    private JobDialogMapper jobDialogMapper;

    @Async("asyncPoolTaskExecutor")
    public void execute(JSONObject data) {
        logger.info("日志入库");
        final String content = data.getString("CONTENT");
        final String uuid = data.getString("UUID");
        JSONArray jsonArray = JSON.parseArray(content);
        if(jsonArray == null){
            return;
        }
        List<JobDialog> jobDialogs = new ArrayList<>();
        for (int i = 0; i< jsonArray.size(); i++) {
            JSONObject jsonObject =(JSONObject) JSONObject.toJSON(jsonArray.get(i));
            JobDialog jobDialog = new JobDialog();
            logger.info("日志入库" + jsonObject.getString("text"));
            jobDialog.setJobId(uuid);
            jobDialog.setPersion(jsonObject.getInteger("person"));
            jobDialog.setContent(jsonObject.getString("text"));
            jobDialog.setCreateTime(new Date());
            jobDialogs.add(jobDialog);
        }
        jobDialogMapper.insertList(jobDialogs);
    }
}
