package com.knowology.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobMapper;
import com.knowology.dao.ShortMsgDetailMapper;
import com.knowology.entity.ActiveMQContaint;
import com.knowology.model.JobDetail;
import com.knowology.model.ShortMsgDetail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * @author : Conan
 * @Description 短信结果接收(MQ)
 * @date : 2019-04-30 16:32
 **/
@Component
public class ShortMsgQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(ShortMsgQueueListener.class);

    @Resource
    private JobDetailMapper jobDetailMapper;
    @Resource
    private ShortMsgDetailMapper shortMsgDetailMapper;
    @Resource
    private JobMapper jobMapper;

    /**
     * 短信消息业务处理
     * @param message
     * @param session
     */
    @JmsListener(destination = ActiveMQContaint.QUEUE_SMS_RESULT, containerFactory = "jmsListenerContainerFactory")
    public void deal(TextMessage message, Session session) {
        String msg = null;
        try {
            msg = message.getText();
            message.acknowledge();
        } catch (JMSException e) {
            logger.error("接收短信失败:", e);
            try {
                session.recover();
            } catch (JMSException e1) {
                logger.error("session recover失败:", e);
            }
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(msg);
        } catch (JSONException e) {
            logger.error("短信消息格式有误:" + msg);
        }
        if (jsonObject != null) {
            String uuid = jsonObject.getString("UUID");
            if (StringUtils.isBlank(uuid)) {
                logger.error("短信消息UUID为空:" + jsonObject);
                return;
            }
            //对于自动回访中短信约访任务的
            if("111".equals(uuid.substring(0,4).toString())){
                String id = uuid.substring(4,uuid.length());
                JobDetail jobDetail = new JobDetail();
                jobDetail.setAppointResult(disposeShortMsgReciveContent(jsonObject.getString("CONTENT")));
                Example example = new Example(ShortMsgDetail.class);
                example.createCriteria().andEqualTo("id", id);
                jobDetailMapper.updateByExampleSelective(jobDetail,example);
                //更改可呼的数量
                JobDetail jobDetail12 = jobDetailMapper.selectRecordresult(Integer.parseInt(id));
                jobMapper.updateByCondition(jobDetailMapper.countCallNum(jobDetail12.getJobName()));
            }else{
                String id = uuid.substring(4,uuid.length());
                //这里是手动发送短信的模块
                ShortMsgDetail shortMsgDetail = new ShortMsgDetail();
                shortMsgDetail.setReceiveContent(jsonObject.getString("CONTENT"));
                shortMsgDetail.setReceiveTime(new Date());
                Example example = new Example(ShortMsgDetail.class);
                example.createCriteria().andEqualTo("uuid", id);
                shortMsgDetailMapper.updateByExampleSelective(shortMsgDetail, example);
            }

        }
    }

    /**
     * 处理一下用户短信回复内容
     * @param content
     * @return
     */
    private String disposeShortMsgReciveContent(String content){
        String result;
        if(content.contains("N") || content.contains("n") || content.contains("不同意") ){
            result = "拒绝";
        }else{
            result = "同意";
        }
        return result;
    }
}
