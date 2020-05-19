package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.dto.JobCountCallNum;
import com.knowology.model.Job;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {

    /**
     * 通过任务名称计算数量
     * @param jobName
     * @return
     */
    Integer countByJobName(@Param("jobName") String jobName);

    /**
     * 更新发送短信的状态
     * @param jobName
     * @return
     */
    int updateSendShortMsgStatus(@Param("jobName") String jobName);

    /**
     * 通过任务名称查找任务
     * @param jobName
     * @return
     */
    Job selectByJobName(@Param("jobName") String jobName);

    /**
     * 根据号码组名称查出所有所属的任务
     * @param telenumGroupName
     * @return
     */
    List<Job> selectByTelenumGroupName(@Param("telenumGroupName") String telenumGroupName);

    /**
     * 根据某些条件改变任务的相应字段
     * @return
     */
    int updateByCondition(JobCountCallNum jobCountCallNum);
}