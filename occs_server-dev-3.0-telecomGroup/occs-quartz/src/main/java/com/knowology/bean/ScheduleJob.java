package com.knowology.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.JobMapper;
import com.knowology.dto.JobDetailCondition;
import com.knowology.entity.ActiveMQContaint;
import com.knowology.model.*;
import com.knowology.provider.Provider;
import com.knowology.service.QuotaService;
import com.knowology.util.SchedulesUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author : Conan
 * @Description 定时任务类继承自QuartzJobBean, DisallowConcurrentExecution禁止并发
 * @date : 2019-08-07 17:24
 **/
@DisallowConcurrentExecution
public class ScheduleJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

    private final String JOB_ONE = "政企交付满意度";
    private final String JOB_TWO = "智慧家庭交付满意率";
    private final String JOB_THREE = "营业厅服务满意率";

    @Autowired
    private JobDetailMapper jobDetailMapper;
    @Autowired
    private JobDetailTaskMapper jobDetailTaskMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private Provider provider;
    @Autowired
    private QuotaService quotaService;
    /**
     * 每次处理数据量
     */
    @Value("${task.jobdetail.num:50}")
    private Integer detailNum;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Job job = (Job) jobExecutionContext.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        job = jobMapper.selectByPrimaryKey(job.getId());
//        TimeStrategy timeStrategy = (TimeStrategy) jobExecutionContext.getMergedJobDataMap().get(Job.TIME_STRATEGY_KEY);
        TimeStrategy timeStrategy = (TimeStrategy) jobExecutionContext.getTrigger().getJobDataMap().get(Job.TIME_STRATEGY_KEY);
        //当前时间在运行范围内，例如：定时任务09：25--18：30执行，则定时任务在09点触发，但是要在25分钟才执行业务逻辑,同理18点的30分钟后不在执行
//        logger.info("执行定时任务" + job.getJobName());
//        //判断当前时间是否在运行范围内
//        if (isJobExecute(timeStrategy)) {
//            logger.info("执行定时任务完成" + job.getJobName()+"结束任务");
//            shutdownJob(jobExecutionContext, job);
//        }else{
//            logger.info("未到达任务执行时间" + job.getJobName());
//        }
        if (isJobExecute(timeStrategy)) {
            logger.info("执行定时任务完成" + job.getJobName()+"结束任务");
            shutdownJob(jobExecutionContext, job);
        }else{
            logger.info("未到达任务执行时间" + job.getJobName());
        }
//        if (isJobExecute(timeStrategy)) {
//            //获取重呼策略
//            StrategyDetail strategyDetail = (StrategyDetail) jobExecutionContext.getMergedJobDataMap().get(Job.STRATEGY_KEY);
//            /// 根据查询条件查询部分待执行任务数据
//            JobDetailCondition condition = new JobDetailCondition();
//            condition.setJobName(job.getJobName());
//            if (strategyDetail == null || "不重呼".equals(strategyDetail.getStrategyName())) {
//                condition.setAgainCallInterval(0);
//                condition.setCallTimes(0);
//                List<String> list = new ArrayList<>();
//                list.add("");
//                condition.setList(list);
//            } else {
//                condition.setAgainCallInterval(strategyDetail.getAgainCallInterval());
//                condition.setCallTimes(strategyDetail.getCallTimes());
//                //重乎原因
//                condition.setList(Arrays.asList(strategyDetail.getFailureCase().split(",")));
//            }
//            //判断是否为自动任务
//            if (isCustomAutoJob(job)) {
//                String jobName = job.getJobName();
//                //自动任务营业厅服务满意率处理
//                boolean b = JOB_THREE.equals(jobName);
//                List<String> provinces = new ArrayList<String>();
//                if (b) {
//                    Set<Object> set = quotaService.opsForRedisQuotaGet(JOB_THREE);
//                    //挑选出配额为0的省份
//                    for (Object quota : set) {
//                        String[] arr = quota.toString().split(":");
//                        if (Integer.parseInt(arr[1]) == 0) {
//                            provinces.add(arr[0]);
//                        }
//                    }
//                    logger.info("达到份额的省份：" + provinces.toString());
//                    //将没有配额的省份放入过不需要呼叫的省份集合中，查询时过滤
//                    condition.setNoCallProvince(provinces);
//                    condition.setDealNum(detailNum);
//                } else {
//                    //政企交付满意度和智慧家庭交付满意率处理
//                    // quota为配额信息，存储在redis中，key格式为 "task任务名的hashcode"，value格式为 "业务类型:省份:服务类型:数量"
//                    String quota = quotaService.opsForRedisQuotaPop(jobName);
//                    // redis中配额信息已执行完毕，则今日任务不在执行
//                    if (StringUtils.isBlank(quota)) {
//                        return;
//                    }
//                    final String[] arr = quota.split(":");
//                    boolean a = (JOB_ONE.equals(jobName) || JOB_TWO.equals(jobName)); //&& arr.length == 4;
//                    if (a) {
//                        int num = Integer.valueOf(arr[3]);
//                        int newNum = num - detailNum;
//                        condition.setBusinessType(arr[0]);
//                        condition.setProvince(arr[1]);
//                        condition.setServiceType(arr[2]);
//                        condition.setDealNum(newNum > 0 ? detailNum : num);
//                        ///剩余配额继续丢到redis中，newNum <= 0 则说明该配额执行完毕不用丢到redis中了
//                        if (newNum > 0) {
//                            quotaService.opsForRedisQuotaAdd(jobName, arr[0] + ":" + arr[1] + ":" + arr[2] + ":" + newNum);
//                        }
//                    }
//                }
//            } else {
//                condition.setDealNum(detailNum);
//            }
//            //继续执行的任务
//            List<JobDetailTask> jobDetails = jobDetailTaskMapper.loadJobDetailTasks(condition);
//            //任务全部执行完成
//            if (null == jobDetails || jobDetails.size() <= 0) {
//                logger.info(job.getJobName() + " 未检索到需要呼叫的数据");
//                //自动任务不停，未发短息的不停
//                if (job.getSendShortMsg() == 0 && job.getIsAuto() == 1) {
//                    return;
//                }
//                //查询是否都已完成重呼，可能存在需要重呼但是未达到重呼时间的数据需要处理
//                if (strategyDetail == null && job.getIsAuto() == 0) {
//                    logger.info(job.getJobName() + "任务完成");
//                    shutdownJob(jobExecutionContext, job);
//                    return;
//                }
////                if (jobDetails.size() == 0 && job.getIsAuto() == 0) {
////                    logger.info(job.getJobName() + "任务完成");
////                    shutdownJob(jobExecutionContext, job);
////                    return;
////                }
//                if (strategyDetail == null) {
//                    return;
//                }
//                final Integer unCompleteRecall = jobDetailMapper.countUnCompleteRecall(strategyDetail.getCallTimes(), job.getJobName());
//                logger.info("剩余数量 ：" + unCompleteRecall);
//                if (unCompleteRecall != null && unCompleteRecall == 0 && job.getIsAuto() == 0) {
//                    shutdownJob(jobExecutionContext, job);
//                    return;
//                }
//            } else {
//                for (JobDetailTask detail : jobDetails) {
//                    JSONObject param = new JSONObject();
//                    param.put("outCallNum", detail.getPassiveNum());
//                    param.put("vrSelected", job.getSceneName());
//                    param.put("playWayOfRobet", job.getPlayMode());
//                    param.put("uuid", detail.getId());
//                    // 添加一个config字段
//                    JSONObject config = new JSONObject();
//                    config.put("客户姓名", detail.getClientName());
//                    config.put("客户电话", detail.getPassiveNum());
//                    param.put("config", config.toJSONString());
//                    logger.info("任务执行呼叫 : " + param.toString());
//                    provider.sendMessage(ActiveMQContaint.QUEUE_CALL, param.toString());
//                }
//                //统一更新dealTimes等状态
//                List<Integer> ids = new ArrayList<>();
//                for (int i = 0; i < jobDetails.size(); i++) {
//                    ids.add(jobDetails.get(i).getId());
//                }
//                //更改呼叫状态
//                jobDetailMapper.incrementDealStatus(ids);
//                //修改呼叫次数
//                jobDetailMapper.incrementDealTimes(ids);
//
//                jobDetailTaskMapper.incrementDealStatus(ids);
//                jobDetailTaskMapper.incrementDealTimes(ids);
//                //更新一下呼叫成功、已经呼叫的任务数量
//                jobMapper.updateByCondition(jobDetailMapper.countCallNum(job.getJobName()));
//            }
//        }
    }

    /**
     * 关闭定时任务，更新定时任务为已完成状态
     *
     * @param jobExecutionContext
     * @param job
     * @return
     */
    private boolean shutdownJob(JobExecutionContext jobExecutionContext, Job job) {
        try {
            //停止定时任务
            jobExecutionContext.getScheduler().pauseJob(SchedulesUtil.getJobKey(job.getJobName()));
            //更新JOB状态，已完成
            Job updateJob = new Job();
            updateJob.setStatus(Job.ScheduleStatus.FINISH.getValue());
            Example example = new Example(Job.class);
            example.createCriteria().andEqualTo("jobName", job.getJobName());
            jobMapper.updateByExampleSelective(updateJob, example);

            //删除任务表里的具体详情数据
            Example detailTask = new Example(JobDetail.class);
            detailTask.createCriteria().andEqualTo("jobName", job.getJobName());
            jobDetailTaskMapper.deleteByExample(detailTask);
        } catch (SchedulerException e) {
            logger.error("关闭定时任务失败[" + job.getJobName() + "]", e);
            return false;
        }
        return true;
    }

    /**
     * 判断当前时间是否在运行范围内,是则返回true
     *
     * @param timeStrategy
     * @return
     */
    private boolean isJobExecute(TimeStrategy timeStrategy) {
        JSONArray jsonArray = JSONArray.parseArray(timeStrategy.getExecuteTime());
        for (int i = 0; i < jsonArray.size(); i++) {
            //当前时间在运行范围内，例如：定时任务09：25--18：30执行，则定时任务在09点触发，但是要在25分钟才执行业务逻辑,同理18点的30分钟后不在执行
            if (LocalTime.now().isAfter(LocalTime.parse(jsonArray.getJSONObject(i).getString("beginTime")))
                    && LocalTime.now().isBefore(LocalTime.parse(jsonArray.getJSONObject(i).getString("endTime")))) {
                logger.info("执行时间内");
                return true;
            }
        }
        logger.info("不再执行时间");
        return false;
    }

    /**
     * if auto job return true; else return false
     *
     * @param job
     * @return
     */
    private boolean isCustomAutoJob(Job job) {
        return (job.getIsAuto() == 1) && (JOB_ONE.equals(job.getJobName()) || JOB_TWO.equals(job.getJobName()) || JOB_THREE.equals(job.getJobName()));
    }
}


