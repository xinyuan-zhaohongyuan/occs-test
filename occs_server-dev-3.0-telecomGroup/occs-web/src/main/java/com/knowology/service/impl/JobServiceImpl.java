package com.knowology.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.async.service.impl.PackJobDetailsAsyncService;
import com.knowology.dao.*;
import com.knowology.model.*;
import com.knowology.request.JobDetailQuery;
import com.knowology.request.JobQuery;
import com.knowology.service.JobService;
import com.knowology.service.StrategyService;
import com.knowology.service.TimeStrategyService;
import com.knowology.util.CronExpressionUtil;
import com.knowology.util.DBDataUtil;
import com.knowology.util.SchedulesUtil;
import com.knowology.util.ShortMsgSchedulesUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Conan
 * @Description 外呼任务Service实现类
 * @date : 2019-04-17 15:19
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;
    @Resource
    private JobDetailMapper jobDetailMapper;
    @Resource
    private PassiveNumDetailMapper passiveNumDetailMapper;
    @Resource
    private PassiveNumMapper passiveNumMapper;
    @Resource
    private BlackListCallMapper blackListCallMapper;
    @Autowired
    private TimeStrategyService timeStrategyService;
    @Autowired
    private StrategyService strategyService;
    @Resource
    private JobDetailTaskMapper jobDetailTaskMapper;

    @Autowired
    private PackJobDetailsAsyncService packJobDetailsAsyncService;

    @Autowired
    private Scheduler scheduler;
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    /**
     * 查询和列表展示
     * @param jobQuery
     * @return
     */
    @Override
    public PageInfo<Job> list(JobQuery jobQuery) {
        PageHelper.startPage(jobQuery.getPageNum(),jobQuery.getPageSize());
        Example example = new Example(Job.class);
//        Example.Criteria criteria = example.selectProperties("id", "jobName", "description", "status", "updateTime", "creator", "total", "success", "complete")
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(jobQuery.getJobName())) {
            criteria.andLike("jobName","%" + jobQuery.getJobName() + "%");
        }
        if (StringUtils.isNotBlank(jobQuery.getStatus())) {
            criteria.andEqualTo("status",jobQuery.getStatus());
        }
        criteria.andEqualTo("isAuto",jobQuery.getIsAuto());
        example.setOrderByClause("CREATE_TIME DESC, UPDATE_TIME DESC");
        List<Job> jobs = jobMapper.selectByExample(example);
        PageInfo<Job> info = new PageInfo<>(jobs);
        return info;
    }

    @Override
    public PageInfo<JobDetail> listDetails(Integer pageNum, Integer pageSize, String jobName) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobDetail> jobDetails = jobDetailMapper.listJobDetails(jobName);
        //电话号码脱敏
        DBDataUtil.desensitizationJobdetail(jobDetails);
        return new PageInfo<>(jobDetails);
    }

    @Override
    public PageInfo<JobDetail> searchDetails(JobDetailQuery jobDetailQuery) {
        PageHelper.startPage(jobDetailQuery.getPageNum(),jobDetailQuery.getPageSize());
        List<JobDetail> jobDetails = jobDetailMapper.searchJobDetails(jobDetailQuery);
        //电话号码脱敏
        DBDataUtil.desensitizationJobdetail(jobDetails);
        PageInfo<JobDetail> info = new PageInfo<>(jobDetails);
        return info;
    }

    @Override
    public List<JobDetail> searchExportDetails(JobDetailQuery jobDetailQuery) {
        List<JobDetail> jobDetails = jobDetailMapper.searchJobDetails(jobDetailQuery);
        return jobDetails;
    }

    @Override
    public void add(JobQuery jobQuery) {
        Job job = new Job();
        job.setJobName(jobQuery.getJobName());
        job.setIsAuto(jobQuery.getIsAuto());
        job.setTelenumGroupName(jobQuery.getPassivenumName());
        if(jobQuery.getPassivenumNameList() != null && jobQuery.getPassivenumNameList().length != 0){
            //处理呼叫号码
            job.setTelenumGroupNameList(DBDataUtil.arrayListTransformArray(jobQuery.getPassivenumNameList()));
            job.setTelenumGroupName(DBDataUtil.arrayTransformString(jobQuery.getPassivenumNameList()));
        }
        job.setSceneName(jobQuery.getSceneName());
        job.setStrategyName(jobQuery.getStrategyName());
        job.setTimeStrategyName(jobQuery.getTimeStrategyName());
        job.setPlayMode(jobQuery.getPlayMode());
        job.setDescription(jobQuery.getDescription());
        job.setCreator(jobQuery.getCreator());
        //新建任务状态为待执行状态，需要手动运行
        job.setStatus(Job.ScheduleStatus.WAIT.getValue());
        TimeStrategy timeStrategy = timeStrategyService.selectOneByStrategyName(jobQuery.getTimeStrategyName());
        StrategyDetail strategyDetail = strategyService.selectOneByStrategyName(jobQuery.getStrategyName());
        //将时间策略转换为cron表达式并赋值给job
        job.setCronExpression(CronExpressionUtil.getCronExpression(timeStrategy.getExecuteTime(),timeStrategy.getRepeatWeek()));
        job.setCreateTime(new Date());
        job.setSendShortMsg(0);
        if(jobQuery.getIsAuto() == 1 && StringUtils.isNotBlank(jobQuery.getShortMsgModelName())){
            job.setShortMsgModelName(jobQuery.getShortMsgModelName());
            job.setWaitReplyTime(jobQuery.getWaitReplyTime());
        }

        //总数
        int total =0;
        for(int i=0;i<DBDataUtil.arrayListTransformArray(jobQuery.getPassivenumNameList()).size();i++){
            total += countPassiveNumTotal(DBDataUtil.arrayListTransformArray(jobQuery.getPassivenumNameList()).get(i));
//            total += passiveNumDetailMapper.countTeleNumGroupDetailByName(DBDataUtil.arrayListTransformArray(jobQuery.getPassivenumNameList()).get(i));
        }
        job.setTotal(total);

        job.setSuccess(0);
        job.setComplete(0);
        job.setReady(0);
//        job.setReady(passiveNumDetailMapper.countTeleNumGroupDetailByName(jobQuery.getPassivenumName()));
        if(jobQuery.getIsAuto() == 1 && StringUtils.isBlank(jobQuery.getShortMsgModelName())){
            job.setSendShortMsg(1);
        }
        //插入外呼任务列表z_job
        jobMapper.insert(job);
        //创建定时任务至quartz相关表
        SchedulesUtil.createScheduleJob(scheduler, job, strategyDetail,timeStrategy);
        if(jobQuery.getIsAuto() == 1 && StringUtils.isNotBlank(jobQuery.getShortMsgModelName())){
//            自动任务需要启动短信任务
            ShortMsgSchedulesUtil.createShortMsgScheduleJob(scheduler, job);
        }
        //异步执行将需要通话的信息详情持久化到表Z_JOB_DETAILS和Z_JOB_DETAILS_TASK
        packJobDetailsAsyncService.insertPassNumToJobDetail(job);
    }

    @Override
    public void update(JobQuery jobQuery) {
        Job job = new Job();
        job.setJobName(jobQuery.getJobName());
        job.setUpdateUser(jobQuery.getUpdateUser());
        job.setUpdateTime(new Date());
        job.setDescription(jobQuery.getDescription());
        job.setPlayMode(jobQuery.getPlayMode());
        job.setSceneName(jobQuery.getSceneName());
        job.setStrategyName(jobQuery.getStrategyName());

        TimeStrategy timeStrategy = timeStrategyService.selectOneByStrategyName(jobQuery.getTimeStrategyName());
        StrategyDetail strategyDetail = strategyService.selectOneByStrategyName(jobQuery.getStrategyName());
        job.setCronExpression(CronExpressionUtil.getCronExpression(timeStrategy.getExecuteTime(),timeStrategy.getRepeatWeek()));
//        job.setStatus(Job.ScheduleStatus.WAIT.getValue());

        job.setTimeStrategyName(jobQuery.getTimeStrategyName());
        job.setShortMsgModelName(jobQuery.getShortMsgModelName());
        job.setWaitReplyTime(jobQuery.getWaitReplyTime());
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("id",jobQuery.getId());
        jobMapper.updateByExampleSelective(job,example);
        SchedulesUtil.updateScheduleJob(scheduler, job, strategyDetail,timeStrategy);
    }

    @Override
    public void delete(JobQuery jobQuery) {
        String jobName = jobQuery.getJobName();
        //删除外呼任务列表记录
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("jobName",jobName);
        jobMapper.deleteByExample(example);
        SchedulesUtil.deleteJob(scheduler, jobName);
        if(jobQuery.getIsAuto() == 1){
            ShortMsgSchedulesUtil.deleteJob(scheduler,jobName+"");
        }
        //删除外呼任务对应的具体详情记录
        Example detail = new Example(JobDetail.class);
        detail.createCriteria().andEqualTo("jobName",jobName);
        jobDetailMapper.deleteByExample(detail);
        //删除任务表里的具体详情数据
        Example detailTask = new Example(JobDetail.class);
        detailTask.createCriteria().andEqualTo("jobName",jobName);
        jobDetailTaskMapper.deleteByExample(detailTask);
    }

    @Override
    public Job getJobById(Integer id) {
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("id",id);
        return jobMapper.selectOneByExample(example);
    }

    @Override
    public void pauseJob(String memberName,Job job) {
        SchedulesUtil.pauseJob(scheduler, job);
        Job updateJob = new Job();
        updateJob.setJobName(job.getJobName());
        updateJob.setUpdateUser(memberName);
        updateJob.setUpdateTime(new Date());
        updateJob.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("jobName",updateJob.getJobName());
        jobMapper.updateByExampleSelective(updateJob,example);
        SchedulesUtil.pauseJob(scheduler,job);
    }

    @Override
    public void resumeJob(String memberName,Job job) {
        SchedulesUtil.resumeJob(scheduler, job);
        updateJobStatus(job.getJobName(), Job.ScheduleStatus.RUN.getValue(),memberName);
    }

    @Override
    public List<PassiveNum> listPassiveNum() {
        Example example = new Example(PassiveNum.class);
        example.selectProperties("telenumGroupName");
        List<PassiveNum> passiveNums = passiveNumMapper.selectByExample(example);
        return passiveNums;
    }

    @Override
    public Integer countByTaskName(String jobName) {
        return jobMapper.countByJobName(jobName);
    }

    @Override
    public void updateJobStatus(String jobName,String jobStatus,String memberName) {
        Job updateJob = new Job();
        updateJob.setStatus(jobStatus);
        updateJob.setUpdateTime(new Date());
        updateJob.setUpdateUser(memberName);
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("jobName",jobName);
        jobMapper.updateByExampleSelective(updateJob,example);
    }

    @Override
    public JobDetail selectRecordresult(Integer id) {
        JobDetail jobDetail =jobDetailMapper.selectRecordresult(id);
        return jobDetail;
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
            if(blackListCallMapper.selectBlackListCallResult(passiveNum+"").size() > 0){
                continue;   //查询黑名单策略
            }
            if (passiveNum == null ){
                continue;
            }
            JobDetail jobDetail = new JobDetail();
            if(StringUtils.isBlank(job.getShortMsgModelName())){
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

    @Override
    public Job getJobByName(String jobName) {
        Example example = new Example(Job.class);
        example.createCriteria().andEqualTo("jobName",jobName);
        return jobMapper.selectOneByExample(example);
    }

    @Override
    public List<String> listAllJobName() {
        Example example = new Example(Job.class);
        example.selectProperties("jobName");
        List<Job> jobs = jobMapper.selectByExample(example);
        return jobs.stream().map(Job::getJobName).collect(Collectors.toList());
    }

    @Override
    public List<String> listJobByScene(String scene) {
        Example example = new Example(Job.class);
        example.selectProperties("jobName")
                .createCriteria().andEqualTo("sceneName",scene);
        List<Job> jobs = jobMapper.selectByExample(example);
        return jobs.stream().map(Job::getJobName).filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> selectByTelenumGroupName(String telenumGroupName) {
        return jobMapper.selectByTelenumGroupName(telenumGroupName);
    }

    @Override
    public void restartJob(String memberName, Job job) {
        if(job.getIsAuto() == 1){
//            自动任务需要启动短信任务
            ShortMsgSchedulesUtil.createShortMsgScheduleJob(scheduler, job);
        }
        //将需要外呼的号码加到jobdetail表中供外呼任务及记录统计分析
        if(job.getTelenumGroupName() != null ){
            List<String> list = Arrays.asList(job.getTelenumGroupName().replace("[","").replace("]","").split(","));
            for(int i=0;i<list.size();i++){
                job.setTelenumGroupName(list.get(i));
                jobDetailMapper.insertList(packJobDetailsByJob(job));
            }
        }
        //添加至备份表中
        List<JobDetailTask> jobDetailTasks = JSONArray.parseArray(JSONArray.toJSONString(jobDetailMapper.listJobDetails(job.getJobName())),JobDetailTask.class);
        jobDetailTaskMapper.insertJobDetailsTaskList(jobDetailTasks);
        SchedulesUtil.resumeJob(scheduler, job);
        updateJobStatus(job.getJobName(), Job.ScheduleStatus.RUN.getValue(),memberName);
    }

    private Integer countPassiveNumTotal(String telenumGroupName){
        Integer total = 0 ;
        Example example = new Example(PassiveNumDetail.class);
        //查出接受回访的电话号列表
        example.selectProperties("phoneNum","clientName","businessHallName")
                .createCriteria()
                .andEqualTo("telenumGroupName",telenumGroupName);
        List<PassiveNumDetail> passiveNums = passiveNumDetailMapper.selectByExample(example);
        if (passiveNums == null || passiveNums.size() <= 0) {
            return null;
        }
        PassiveNumDetail passiveNum = null;
        for (int i=0; i<passiveNums.size(); i++) {
            passiveNum = passiveNums.get(i);
            if (blackListCallMapper.selectBlackListCallResult(passiveNum.getPhoneNum() + "").size() > 0) {
                //查询黑名单策略
                continue;
            }
            total++;
        }
        return total;
    }
}
