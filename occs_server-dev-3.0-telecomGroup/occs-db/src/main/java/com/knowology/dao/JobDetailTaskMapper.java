package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.dto.JobDetailCondition;
import com.knowology.model.JobDetailTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobDetailTaskMapper extends MyMapper<JobDetailTask> {

    /**
     * 查询待执行任务
     * @param condition
     * @return
     */
    List<JobDetailTask> loadJobDetailTasks(JobDetailCondition condition);

    /**
     * DEAL_TIMES自增一,即处理次数加一
     * @param ids
     */
    void incrementDealTimes(List<Integer> ids);

    /**
     * DEAL_STATUS状态改为已外呼
     * @param ids
     */
    void incrementDealStatus(List<Integer> ids);

    int insertJobDetailsTaskList(List<JobDetailTask> jobDetailTasks);

    /**
     * 详情里短信发送状态统一更改为未回
     * @param jobName
     */
    void updateSendShortMsgStatus(@Param("jobName") String jobName);

    /**
     * 详情里短信发送状态统一更改为是
     * @param ids
     */
    void incrementSendShortMsg(List<Integer> ids);

    /**
     * 根据id删除备份表数据
     * @param id
     * @return
     */
    int deleteTask(@Param("id") Integer id);

    /**
     * 根据任务名称删除备份表数据
     * @param jobName
     * @return
     */
    int deleteTaskByJobName(@Param("jobName") String jobName);
}