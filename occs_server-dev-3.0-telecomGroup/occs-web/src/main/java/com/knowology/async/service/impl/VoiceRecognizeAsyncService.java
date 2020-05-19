package com.knowology.async.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.VoiceRecognizeMapper;
import com.knowology.model.VoiceRecognize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用一个异步任务来执行呼叫后回执通话内容的语音分段工作
 *
 * @author xullent
 **/
@Service
public class VoiceRecognizeAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(VoiceRecognizeAsyncService.class);
    @Resource
    private VoiceRecognizeMapper voiceRecognizeMapper;
    @Resource
    private JobDetailMapper jobDetailMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async("asyncPoolTaskExecutor")
    public void execute(JSONObject data){
        final String jobId = data.getString("UUID");
        final String callTime = data.getString("answerTime");
        final String content = data.getString("CONTENT");
        String passNum = jobDetailMapper.selectPassNumbyJobId(jobId);
        JSONArray jsonArray = JSON.parseArray(content);
        if(jsonArray == null){
            return;
        }
        int j=0;
        for (int i = 0; i< jsonArray.size(); i++) {
            JSONObject jsonObject =(JSONObject) JSONObject.toJSON(jsonArray.get(i));
            if(jsonObject.getInteger("person") == 1){
                j++;
                VoiceRecognize voiceRecognize = new VoiceRecognize();
                String beginTime = jsonObject.getString("beginTime");
                String endTime = jsonObject.getString("endTime");
                voiceRecognize.setJobId(Integer.parseInt(jobId));
                try {
                    voiceRecognize.setCallTime(sdf.parse(callTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                logger.info("【开始时间】" + getDateDiffBySecond(callTime,beginTime).toString());
                voiceRecognize.setBeginTime(getDateDiffBySecond(callTime,beginTime).toString());
                voiceRecognize.setEndTime(getDateDiffBySecond(callTime,endTime).toString());
                logger.info("【结束时间】" + getDateDiffBySecond(callTime,endTime).toString());
                voiceRecognize.setUrl("/home/voice/var/" + jobId + "_" + j + ".wav");
                voiceRecognize.setPassiveNum(passNum);
                voiceRecognize.setLabelStatus("未标注");
                voiceRecognize.setRecognizeResult(jsonObject.getString("text"));
                voiceRecognizeMapper.insert(voiceRecognize);
            }
        }
    }

    private Integer getDateDiffBySecond(String dateStr1,String dateStr2){
        Integer result = 0;
        System.out.println("result : " + result);
        try {
            Date date1 = sdf.parse(dateStr1);
            Date date2 = sdf.parse(dateStr2);
            long a = date1.getTime();
            long b = date2.getTime();
            result = (int)((b - a) / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
