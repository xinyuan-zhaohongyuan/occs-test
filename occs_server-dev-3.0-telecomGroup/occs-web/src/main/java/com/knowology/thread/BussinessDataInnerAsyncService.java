package com.knowology.thread;

import com.knowology.dao.BussinessDataMapper;
import com.knowology.dao.JobDetailMapper;
import com.knowology.dao.JobDetailTaskMapper;
import com.knowology.dao.JobMapper;
import com.knowology.model.BussinessData;
import com.knowology.model.Job;
import com.knowology.model.JobDetail;
import com.knowology.model.JobDetailTask;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 异步任务,异步任务操作excel读入的数据,入库
 *
 * @author xullent
 */
@Service
public class BussinessDataInnerAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(BussinessDataInnerAsyncService.class);

    private final String JOB_ONE = "营业厅服务满意率";
    private final String JOB_TWO = "政企交付满意度";
    private final String JOB_THREE = "智慧家庭交付满意率";

    @Resource
    private BussinessDataMapper bussinessDataMapper;

    @Resource
    private JobDetailMapper jobDetailMapper;

    @Resource
    private JobDetailTaskMapper jobDetailTaskMapper;

    @Resource
    private JobMapper jobMapper;

    /**
     * 异步执行入数据接收表的任务
     * @param list
     */
    @Async("asyncPoolTaskExecutor")
    public void innerBussinessData(List<BussinessData> list){
        logger.info("【数据入库】");
        bussinessDataMapper.insertList(list);
    }

    /**
     * 异步执行入任务表的任务
     * @param
     */
    @Async("asyncPoolTaskExecutor")
    public void innerDataToJobTask() throws SchedulerException {
        //营业厅服务满意率
        innerDataToJobDetail(1,JOB_ONE);
        //政企交付满意度
        innerDataToJobDetail(2,JOB_TWO);
        //智慧家庭交付满意率
        innerDataToJobDetail(3,JOB_THREE);
    }

    private void innerDataToJobDetail(Integer flag,String jobName) throws SchedulerException {
        List<BussinessData> bussinessDataList = bussinessDataMapper.selectInnerData(flag);
        Job job = jobMapper.selectByJobName(jobName);
        List<JobDetail> jobDetails = new ArrayList<>();
        List<JobDetailTask> jobDetailTasks = new ArrayList<>();
        if(bussinessDataList != null && bussinessDataList.size() > 0) {
            for (BussinessData bussinessData : bussinessDataList) {
                JobDetail jobDetail = new JobDetail();
                JobDetailTask jobDetailTask = new JobDetailTask();
                jobDetail.setJobName(jobName);
                jobDetail.setPassiveNum(bussinessData.getPhoneNumber());
                jobDetail.setScene(job.getSceneName());
                jobDetail.setDealStatus("未外呼");
                jobDetail.setDealTimes(0);
                jobDetail.setInfoId(bussinessData.getId());
                jobDetail.setSendShortmsg(1);
                jobDetail.setAppointResult("不发");

                jobDetailTask.setJobName(jobName);
                jobDetailTask.setPassiveNum(bussinessData.getPhoneNumber());
                jobDetailTask.setScene(job.getSceneName());
                jobDetailTask.setDealStatus("未外呼");
                jobDetailTask.setDealTimes(0);
                jobDetailTask.setAppointResult("不发");

                //产品类型字段来作为业务类型
                jobDetailTask.setBusinessType(bussinessData.getProduceType());
                jobDetailTask.setProvince(bussinessData.getProvince());
                jobDetailTask.setServiceType(bussinessData.getServiceType());
                jobDetailTask.setSendShortmsg(1);

                jobDetails.add(jobDetail);
                jobDetailTasks.add(jobDetailTask);

            }
            jobDetailMapper.insertList(jobDetails);
            jobDetailTaskMapper.insertList(jobDetailTasks);
            //更新一下总数
//            JobCountCallNum jobCountCallNum = new JobCountCallNum();
//            jobCountCallNum.setJobName(jobName);
//            jobCountCallNum.setTotal(jobDetails.size());
//            jobCountCallNum.setReady(jobDetails.size());

//            jobMapper.updateByCondition(jobCountCallNum);
        }
    }

    /**
     * 执行每日清除一次外呼表中的三个任务
     */
    public void clearDataToJobTask(){
        jobDetailTaskMapper.deleteTaskByJobName(JOB_ONE);
        jobDetailTaskMapper.deleteTaskByJobName(JOB_TWO);
        jobDetailTaskMapper.deleteTaskByJobName(JOB_THREE);
    }
}
