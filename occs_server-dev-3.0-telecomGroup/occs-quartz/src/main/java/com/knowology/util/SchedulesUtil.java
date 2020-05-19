package com.knowology.util;

import com.knowology.annotation.NotThreadSafe;
import com.knowology.bean.ShortMsgScheduleJob;
import com.knowology.bean.ScheduleJob;
import com.knowology.model.Job;
import com.knowology.model.StrategyDetail;
import com.knowology.model.TimeStrategy;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Conan
 * @Description 定时任务工具类
 * @date : 2019-04-17 15:47
 **/

public class SchedulesUtil {
    private static final Logger logger = LoggerFactory.getLogger(SchedulesUtil.class);

    private static final String JOB_NAME_PREFIX = "TASK_";

    private static final String TRIGGER_NAME_PREFIX = "TRIGGER_";

    private static final String JOB_GROUP = "OCCS";

    /**
     * 创建定时任务
     * @param scheduler
     * @param job
     */
    public static void createScheduleJob(Scheduler scheduler, Job job, StrategyDetail strategyDetail, TimeStrategy timeStrategy) {
        try {
            //构建Job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobName())).build();

            //表达式调度构建器
            //根据时间策略转换的cron表达式创建调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //按新的cronExpression表达式构建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobName()))
                    .withSchedule(cronScheduleBuilder).build();
            cronTrigger.getJobDataMap().put(Job.STRATEGY_KEY, strategyDetail);
            cronTrigger.getJobDataMap().put(Job.TIME_STRATEGY_KEY, timeStrategy);
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Job.JOB_PARAM_KEY, job);
            jobDetail.getJobDataMap().put(Job.STRATEGY_KEY, strategyDetail);
            jobDetail.getJobDataMap().put(Job.TIME_STRATEGY_KEY, timeStrategy);
            //组装任务组件
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if (StringUtils.equals(job.getStatus(), Job.ScheduleStatus.WAIT.getValue())) {
                //创建后暂停
                pauseJob(scheduler, job);
            }
        }catch (SchedulerException e) {
            logger.error("创建定时任务失败",e);
        }
    }
    /**
     * 创建定时任务
     * @param scheduler
     * @param job
     */
    public static void createShortMsgScheduleJob(Scheduler scheduler, Job job) {
        try {
            //构建Job信息
            JobDetail jobDetail = JobBuilder.newJob(ShortMsgScheduleJob.class).withIdentity(getJobKey(job.getJobName())).build();

            //表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
            //按新的cronExpression表达式构建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobName()))
                    .withSchedule(cronScheduleBuilder).build();
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Job.JOB_PARAM_KEY, job);
            //组装任务组件
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if (StringUtils.equals(job.getStatus(), Job.ScheduleStatus.WAIT.getValue())) {
                pauseJob(scheduler, job);
            }
        }catch (SchedulerException e) {
            logger.error("创建定时任务失败",e);
        }
    }

    /**
     * 更新定时任务
     */
    @NotThreadSafe("scheduler中的trigger状态有修改，该方法线程不安全，避免并发场景调用")
    public static void updateScheduleJob(Scheduler scheduler, Job job, StrategyDetail strategyDetail, TimeStrategy timeStrategy) {
        try {
            TriggerKey triggerKey = getTriggerKey(job.getJobName());
//            CronTrigger trigger = getCronTrigger(scheduler, job.getJobName());
//            // 表达式调度构建器
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
//                    .withMisfireHandlingInstructionDoNothing();


            //根据时间策略转换的cron表达式创建调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            //按新的cronExpression表达式构建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobName()))
                    .withSchedule(cronScheduleBuilder).build();
            cronTrigger.getJobDataMap().put(Job.STRATEGY_KEY, strategyDetail);
            cronTrigger.getJobDataMap().put(Job.TIME_STRATEGY_KEY, timeStrategy);

//            if (trigger == null) {
//                throw new SchedulerException("获取Cron表达式失败");
//            } else {
//                // 按新的cronExpression表达式重新构建trigger
//                trigger = trigger.getTriggerBuilder()
//                        .withIdentity(triggerKey)
////                    .build();
////                // 参数
////                trigger.getJobDataMap().put(JOB_PARAM_KEY, job);
////            }        .withSchedule(scheduleBuilder)
//
            scheduler.rescheduleJob(triggerKey, cronTrigger);

            // 暂停任务
            if (StringUtils.equalsAny(job.getStatus(),
                    Job.ScheduleStatus.PAUSE.getValue(),
                    Job.ScheduleStatus.WAIT.getValue())) {
                pauseJob(scheduler, job);
            }

        } catch (SchedulerException e) {
            logger.error("更新定时任务失败", e);
        }
    }

    /**
     * 暂停定时任务
     * @param scheduler
     * @param job
     */
    public static void pauseJob(Scheduler scheduler, Job job) {
        try {
            scheduler.pauseJob(getJobKey(job.getJobName()));
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
        }
    }

    /**
     * 运行定时任务
     * @param scheduler
     * @param job
     */
    public static void resumeJob(Scheduler scheduler, Job job) {
        try {
            scheduler.resumeJob(getJobKey(job.getJobName()));
        } catch (SchedulerException e) {
            logger.error("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     * @param scheduler
     * @param jobName
     */
    public static void deleteJob(Scheduler scheduler, String jobName) {
        CronTrigger cronTrigger = getCronTrigger(scheduler, jobName);
        TriggerKey triggerKey = getTriggerKey(jobName);
        if (cronTrigger == null ) {
            return;
        }
        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(getJobKey(jobName));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败", e);
        }
    }

    /**
     * 获取任务key
     * @param jobName
     * @return
     */
    public static JobKey getJobKey(String jobName) {
        return JobKey.jobKey(JOB_NAME_PREFIX + jobName,JOB_GROUP);
    }

    /**
     * 获取trigger的key
     * @param jobName
     * @return
     */
    private static TriggerKey getTriggerKey(String jobName) {
        return TriggerKey.triggerKey(TRIGGER_NAME_PREFIX+jobName,JOB_GROUP);
    }

    /**
     * 获取Cron的trigger
     * @param scheduler
     * @param jobName
     * @return
     */
    private static CronTrigger getCronTrigger(Scheduler scheduler, String jobName) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobName));
        } catch (SchedulerException e) {
            logger.error("获取Cron表达式失败",e);
        }
        return null;
    }
}
