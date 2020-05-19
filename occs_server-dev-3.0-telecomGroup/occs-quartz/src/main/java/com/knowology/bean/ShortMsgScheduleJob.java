package com.knowology.bean;

import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.JobMapper;
import com.knowology.dao.PassiveNumDetailMapper;
import com.knowology.dto.JobDetailCondition;
import com.knowology.entity.ActiveMQContaint;
import com.knowology.model.Job;
import com.knowology.model.JobDetail;
import com.knowology.model.PassiveNumDetail;
import com.knowology.po.ShortMsgJobDetail;
import com.knowology.provider.Provider;
import com.knowology.util.DateUtil;
import com.knowology.util.ShortMsgSchedulesUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 专门用来跑短信定时任务
 * @author : xullent
 * @Description 定时任务类继承自QuartzJobBean, DisallowConcurrentExecution禁止并发
 **/
@DisallowConcurrentExecution
public class ShortMsgScheduleJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(ShortMsgScheduleJob.class);
    @Autowired
    private JobDetailMapper jobDetailMapper;
    @Autowired
    private JobDetailTaskMapper jobDetailTaskMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private Provider provider;
    @Autowired
    private PassiveNumDetailMapper passiveNumDetailMapper;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 每次处理发送短信的数据量
     */
    @Value("${task.shortMsgJobDetail.num}")
    private Integer detailNum;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Job job = (Job) jobExecutionContext.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        logger.info("执行短信任务【" + job.getJobName() + "】");
        JobDetailCondition condition = new JobDetailCondition();
        condition.setJobName(job.getJobName());
        condition.setDealNum(detailNum);
        List<ShortMsgJobDetail> shortMsgJobDetails = jobDetailMapper.loadShortMsgJobDetails(condition);
        if (null == shortMsgJobDetails || shortMsgJobDetails.size() <= 0) {
            // 判断是否达到等待时间
            logger.info("间隔时间 ： " + DateUtil.differentDaysByHour(new Date(), job.getCreateTime()));
            if(DateUtil.differentDaysByHour(new Date(), job.getCreateTime()) > job.getWaitReplyTime()){
                //到达等待时间，将未回复短信的用户回复状态统一更改为未回状态
                jobDetailMapper.updateSendShortMsgStatus(job.getJobName());
                jobDetailTaskMapper.updateSendShortMsgStatus(job.getJobName());
                jobMapper.updateSendShortMsgStatus(job.getJobName());
                //更改可呼的数量
                JobDetail jobDetail12 = jobDetailMapper.selectJobName(job.getId());
                logger.info("更改可以呼叫的数量 : " + jobDetail12);
                logger.info(jobDetailMapper.countCallNum(jobDetail12.getJobName()).toString());
                jobMapper.updateByCondition(jobDetailMapper.countCallNum(jobDetail12.getJobName()));
                try {
                    logger.info("结束短信任务 : " + ShortMsgSchedulesUtil.getJobKey(job.getJobName()));
                    //所有的短信发送完毕且达到等待时间以后停止短信定时任务
                    jobExecutionContext.getScheduler().pauseJob(ShortMsgSchedulesUtil.getJobKey(job.getJobName()));
                } catch (SchedulerException e) {
                    logger.error("关闭短信定时任务失败【" + job.getJobName() + "】", e);
                }
            }
        }else {
            sendMsg(shortMsgJobDetails);
            //统一更改发送短信的状态
//            final List<Long> ids = shortMsgJobDetails.stream().map(ShortMsgJobDetail::getId).collect(Collectors.toList());
            List<Integer> ids=new ArrayList<>();
            for(int i=0;i<shortMsgJobDetails.size();i++){
                ids.add(shortMsgJobDetails.get(i).getId());
            }
            jobDetailMapper.incrementSendShortMsg(ids);
            jobDetailTaskMapper.incrementSendShortMsg(ids);
        }
    }

    /**
     *异步发送短信
     * @param shortMsgJobDetails
     */
    @Async("asyncPoolTaskExecutor")
    public void sendMsg(List<ShortMsgJobDetail> shortMsgJobDetails) {
        for(ShortMsgJobDetail j : shortMsgJobDetails){
            PassiveNumDetail passiveNumDetail = passiveNumDetailMapper.selectTeleNumGroupDetailByName(j.getPassiveNum());
            String msgTemplate  = j.getShortMsgContent();
            logger.info("短信任务发送 : " + packShortMsg(passiveNumDetail,msgTemplate,j.getId()));
            provider.sendMessage(ActiveMQContaint.QUEUE_SMS,packShortMsg(passiveNumDetail,msgTemplate,j.getId()));
        }
    }

    /**
     * 根据短信模板和电话详细信息生成具体短信内容
     * @param passiveNumDetail
     * @param msgTemplate
     * @return
     */
    private String packShortMsg(PassiveNumDetail passiveNumDetail, String msgTemplate,Integer uuid) {
        if (passiveNumDetail.getCreateTime() == null) {
            passiveNumDetail.setCreateTime(new Date());
        }
        if (passiveNumDetail.getBussinessType() == null) {
            passiveNumDetail.setBussinessType("");
        }
        if (passiveNumDetail.getBusinessHallName() == null) {
            passiveNumDetail.setBusinessHallName("");
        }
        if (passiveNumDetail.getClientName() == null) {
            passiveNumDetail.setClientName("");
        }

        String msg = msgTemplate.replace("[姓名]", passiveNumDetail.getClientName())
                .replace("[订单日期]", sdf.format(passiveNumDetail.getCreateTime()))
                .replace("[营业厅名称]", passiveNumDetail.getBusinessHallName())
                .replace("[业务名称]", passiveNumDetail.getBussinessType());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", "111" + uuid);
        jsonObject.put("phoneNum",passiveNumDetail.getPhoneNum());
        jsonObject.put("msgContent",msg);
        return jsonObject.toJSONString();
    }

}
