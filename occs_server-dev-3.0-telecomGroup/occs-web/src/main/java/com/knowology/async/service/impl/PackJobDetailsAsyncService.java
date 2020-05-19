package com.knowology.async.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.knowology.dao.BlackListCallMapper;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.PassiveNumDetailMapper;
import com.knowology.model.Job;
import com.knowology.model.JobDetail;
import com.knowology.model.JobDetailTask;
import com.knowology.model.PassiveNumDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 异步执行号码组入任务表数据
 * @author xullent
 * @date 2020/1/6 11:01
 */
@Service
public class PackJobDetailsAsyncService {

    @Resource
    private JobDetailMapper jobDetailMapper;
    @Resource
    private PassiveNumDetailMapper passiveNumDetailMapper;
    @Resource
    private JobDetailTaskMapper jobDetailTaskMapper;
    @Resource
    private BlackListCallMapper blackListCallMapper;


    /**
     * 异步执行号码插入任务表
     * @param job
     */
    @Async("asyncPoolTaskExecutor")
    public void insertPassNumToJobDetail(Job job){
        //将需要外呼的号码加到jobdetail表中供外呼任务及记录统计分析
        jobDetailMapper.insertList(packJobDetailsByJob(job));
        //添加至备份表中
        List<JobDetailTask> jobDetailTasks = JSONArray.parseArray(JSONArray.toJSONString(jobDetailMapper.listJobDetails(job.getJobName())),JobDetailTask.class);
        jobDetailTaskMapper.insertJobDetailsTaskList(jobDetailTasks);
    }

    /**
     * 根据外呼任务查出号码组详情信息，然后构造List<JobDetail>,后续批量存入外呼任务详情记录表供外呼调用及统计分析
     * @param job
     * @return
     */
    private List<JobDetail> packJobDetailsByJob(Job job) {
        if (job == null || StringUtils.isBlank(job.getJobName())) {
            return null;
        }
        List<JobDetail> list = new ArrayList<>();
        for(int i=0;i<job.getTelenumGroupNameList().size();i++){
            job.setTelenumGroupName(job.getTelenumGroupNameList().get(i));
            list.addAll(insertJobDeatilsByPassNum(job));
        }
        return list;
    }

    private List<JobDetail> insertJobDeatilsByPassNum(Job job){
        List<JobDetail> list = new ArrayList<>();
        Example example = new Example(PassiveNumDetail.class);
        //查出接受回访的电话号列表
        example.selectProperties("phoneNum","clientName","businessHallName")
                .createCriteria()
                .andEqualTo("telenumGroupName",job.getTelenumGroupName());
        List<PassiveNumDetail> passiveNums = passiveNumDetailMapper.selectByExample(example);
        if (passiveNums == null || passiveNums.size() <= 0) {
            return null;
        }
        PassiveNumDetail passiveNum = null;
        for (int i=0; i<passiveNums.size(); i++) {
            passiveNum = passiveNums.get(i);
            // TODO: 2019/8/7 黑名单可以从缓存里面一次性取出来,更新黑名单时同步更新缓存
            if (blackListCallMapper.selectBlackListCallResult(passiveNum.getPhoneNum() + "").size() > 0) {
                //查询黑名单策略
                continue;
            }
            JobDetail jobDetail = new JobDetail();
            if (StringUtils.isBlank(job.getShortMsgModelName())) {
                //不发送短信
                jobDetail.setAppointResult("不发");
            }
            jobDetail.setUuid(UUID.randomUUID().toString());
            jobDetail.setScene(job.getSceneName());
            jobDetail.setJobName(job.getJobName());
            jobDetail.setDealTimes(0);
            jobDetail.setDealStatus(Job.WAIT_CALL);
            jobDetail.setPassiveNum(passiveNum.getPhoneNum());
            jobDetail.setClientName(passiveNum.getClientName());
            jobDetail.setBusinessHallName(passiveNum.getBusinessHallName());
            list.add(jobDetail);
        }
        //更新这些号码的状态为同步状态
        final List<Integer> ids = passiveNums.stream().map(PassiveNumDetail::getId).collect(Collectors.toList());
        passiveNumDetailMapper.incrementSynChronStatus(ids);
        return list;
    }
}
