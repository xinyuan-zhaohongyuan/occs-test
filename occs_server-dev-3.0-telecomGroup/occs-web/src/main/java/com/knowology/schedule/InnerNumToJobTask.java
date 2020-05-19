package com.knowology.schedule;

import com.alibaba.fastjson.JSONArray;
import com.knowology.dao.BlackListCallMapper;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.PassiveNumDetailMapper;
import com.knowology.model.*;
import com.knowology.service.JobService;
import com.knowology.service.PassiveNumService;
import com.knowology.util.ShortMsgSchedulesUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 号码组数据同步至任务详情
 * @author xullent
 */
@ConditionalOnProperty(prefix = "occs.schedule.inner-num-to-job-task",value = "enable",havingValue = "true")
@Component
public class InnerNumToJobTask {

    private static final Logger logger = LoggerFactory.getLogger(InnerNumToJobTask.class);
    @Autowired
    private JobService jobService;
    @Autowired
    private PassiveNumService passiveNumService;
    @Resource
    private JobDetailMapper jobDetailMapper;
    @Resource
    private JobDetailTaskMapper jobDetailTaskMapper;
    @Resource
    private BlackListCallMapper blackListCallMapper;
    @Resource
    private PassiveNumDetailMapper passiveNumDetailMapper;
    @Autowired
    private Scheduler scheduler;

    /**
     * 每次处理数据量
     */
    @Value("${task.synPassNum.num}")
    private Integer detailNum;

    /**
     * 执行同步任务
     */
//    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() throws SchedulerException {
        List<PassiveNum> passiveNumList = passiveNumService.selectAll();
        for(PassiveNum passiveNum : passiveNumList){
            //查出该号码组所属的任务
            List<Job> jobs = jobService.selectByTelenumGroupName(passiveNum.getTelenumGroupName());
            List<PassiveNumDetail> passiveNumDetails = passiveNumService.selectNoSynByTelenumGroupName(passiveNum.getTelenumGroupName(),detailNum);
            if(passiveNumDetails == null || passiveNumDetails.size() <= 0){
                continue;
            }
            for(Job job : jobs){
                //对于已完成的任务不进行处理
                if(Job.ScheduleStatus.FINISH.equals(job.getStatus())){
                    continue;
                }

                synChronData(job,passiveNumDetails);
                //对于已经执行完发送短信的任务，要重启短信任务
                if(job.getSendShortMsg() == 1){
                    scheduler.resumeJob(ShortMsgSchedulesUtil.getJobKey(job.getJobName()));
                }
                logger.info("执行新增号码向对应任务同步功能--" + job.getJobName());
            }
            final List<Integer> ids = passiveNumDetails.stream().map(PassiveNumDetail::getId).collect(Collectors.toList());
            passiveNumDetailMapper.incrementSynChronStatus(ids);
        }
    }

    /**
     * 根据号码组详情信息，然后构造List<JobDetail>,批量存入外呼任务详情记录表供外呼调用及统计分析
     * @param job
     * @return
     */
    private void synChronData(Job job,List<PassiveNumDetail> passiveNums) {
        List<JobDetail> list = new ArrayList<>();
        if (passiveNums == null || passiveNums.size() <= 0) {
            return;
        }
        for (int i=0; i<passiveNums.size(); i++) {
            PassiveNumDetail passiveNum = passiveNums.get(i);
            // TODO: 2019/8/7 黑名单可以从缓存里面一次性取出来,更新黑名单时同步更新缓存,后续跟着一块改
            if(blackListCallMapper.selectBlackListCallResult(passiveNum+"").size() > 0){
                continue;   //查询黑名单策略
            }
            if (passiveNum == null ){
                continue;
            }
            JobDetail jobDetail = new JobDetail();
            if(job.getIsAuto() == 0){
                jobDetail.setAppointResult("不发");
            }
            jobDetail.setScene(job.getSceneName());
            jobDetail.setJobName(job.getJobName());
            jobDetail.setDealTimes(0);
            jobDetail.setDealStatus(Job.WAIT_CALL);
            jobDetail.setPassiveNum(passiveNum.getPhoneNum());
            jobDetail.setClientName(passiveNum.getClientName());
            jobDetail.setBusinessHallName(passiveNum.getBusinessHallName());
            list.add(jobDetail);
        }
        jobDetailMapper.insertList(list);
        //添加至任务表
        List<JobDetailTask> jobDetailTasks = JSONArray.parseArray(JSONArray.toJSONString(list),JobDetailTask.class);
        jobDetailTaskMapper.insertJobDetailsTaskList(jobDetailTasks);
    }
}
