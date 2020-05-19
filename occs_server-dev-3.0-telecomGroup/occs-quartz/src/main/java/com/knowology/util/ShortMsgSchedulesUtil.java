package com.knowology.util;

import com.knowology.annotation.NotThreadSafe;
import com.knowology.bean.ShortMsgScheduleJob;
import com.knowology.model.Job;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信任务工具类
 * @author xullent
 */
public class ShortMsgSchedulesUtil {
    private static final Logger logger = LoggerFactory.getLogger(ShortMsgSchedulesUtil.class);

    private static final String JOB_NAME_PREFIX = "TASK_SHORTMSG_";

    private static final String TRIGGER_NAME_PREFIX = "TRIGGER_SHORTMSG_";

    private static final String JOB_GROUP = "OCCS_SHORTMSG";
//    暂时写死，需改为可配置
    private static final String cron = "0/30 * * * * ?";

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
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            //按新的cronExpression表达式构建一个trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobName()))
                    .withSchedule(cronScheduleBuilder).build();
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Job.JOB_PARAM_KEY, job);
            //组装任务组件
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if (StringUtils.equals(job.getStatus(), Job.ScheduleStatus.WAIT.getValue())) {
                //创建任务后就启动短信任务
                resumeJob(scheduler, job);
            }
        }catch (SchedulerException e) {
            logger.error("创建定时任务失败",e);
        }
    }

    /**
     * 更新定时任务
     */
    @NotThreadSafe("scheduler中的trigger状态有修改，该方法线程不安全，避免并发场景调用")
    public static void updateScheduleJob(Scheduler scheduler, Job job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job.getJobName());
            // 表达式调度构建器
            CronTrigger trigger = getCronTrigger(scheduler, job.getJobName());
            scheduler.rescheduleJob(triggerKey, trigger);
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
