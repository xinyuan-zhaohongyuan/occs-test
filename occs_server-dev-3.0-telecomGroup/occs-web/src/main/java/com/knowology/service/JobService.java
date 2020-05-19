package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.Job;
import com.knowology.model.JobDetail;
import com.knowology.model.PassiveNum;
import com.knowology.request.JobDetailQuery;
import com.knowology.request.JobQuery;

import java.util.List;

/**
 * @author : Conan
 * @Description 外呼任务Service
 * @date : 2019-04-17 15:18
 **/

public interface JobService {
    /**
     * 任务列表
     *
     * @param jobQuery
     * @return
     */
    PageInfo<Job> list(JobQuery jobQuery);

    /**
     * 任务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<JobDetail> listDetails(Integer pageNum, Integer pageSize, String jobName);

    /**
     * 任务外呼记录查询
     * @param jobDetailQuery
     * @return
     */
    PageInfo<JobDetail> searchDetails(JobDetailQuery jobDetailQuery);


    /**
     * 查询需要导出excel的任务详情数据
     * @param jobDetailQuery
     * @return
     */
    List<JobDetail> searchExportDetails(JobDetailQuery jobDetailQuery);

    /**
     * 添加任务
     *
     * @param jobQuery
     */
    void add(JobQuery jobQuery);


    /**
     * 更新任务
     *
     * @param jobQuery
     */
    void update(JobQuery jobQuery);

    /**
     * 删除任务
     *
     * @param jobQuery
     */
    void delete(JobQuery jobQuery);

    /**
     * 根据ID获取唯一Job
     *
     * @param id
     * @return
     */
    Job getJobById(Integer id);

    /**
     * 暂停指定任务
     *
     * @param id
     */
    void pauseJob(String memberName,Job id);

    /**
     * 恢复指定任务运行状态
     *
     * @param id
     */
    void resumeJob(String memberName,Job id);

    /**
     * 加载号码组名称列表
     * @return
     */
    List<PassiveNum> listPassiveNum();


    /**
     * 查询该任务名称是否已经存在
     * @param jobName
     * @return
     */
    Integer countByTaskName(String jobName);

    /**
     * 更新任务状态
     */
    void updateJobStatus(String jobName, String jobStatus,String memberName);

    /**
     * 根据外呼ID查询录音相关
     * @param id
     * @return
     */
    JobDetail selectRecordresult(Integer id);

    /**
     * 通过任务名查询
     * @param jobName
     * @return
     */
    Job getJobByName(String jobName);

    /**
     * 查询所有任务名称
     * @return
     */
    List<String> listAllJobName();

    /**
     * 根据场景查询名称任务列表
     * @param scene
     * @return
     */
    List<String> listJobByScene(String scene);

    /**
     * 根据号码组名称查出所属任务
     * @param telenumGroupName
     * @return
     */
    List<Job> selectByTelenumGroupName(String telenumGroupName);

    /**
     * 重启任务状态
     *
     * @param job
     */
    void restartJob(String memberName,Job job);

}
