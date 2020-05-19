package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.dto.JobCountCallNum;
import com.knowology.dto.JobDetailCondition;
import com.knowology.excelVo.BussinessHallResult;
import com.knowology.model.JobDetail;
import com.knowology.po.ShortMsgJobDetail;
import com.knowology.request.JobDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobDetailMapper extends MyMapper<JobDetail> {
    /**
     * 加载JobDetail列表
     * @param jobName
     * @return
     */
    List<JobDetail> listJobDetails(@Param("jobName") String jobName);
    /**
     * 查询满足条件的JobDetail列表
     * @param jobDetailQuery
     * @return
     */
    List<JobDetail> searchJobDetails(JobDetailQuery jobDetailQuery);

    /**
     * 批量插入待外呼号码数据
     * @param list
     */
    void insertBatch(List<JobDetail> list);

    /**
     * 根据任务Id查询所有jobdetail
     * @param id
     * @return
     */
    List<JobDetail> selectJobDetails(Integer id);
    /**
     * 根据外呼ID查询录音相关
     * @param id
     * @return
     */
    JobDetail selectRecordresult(Integer id);

    /**
     * DEAL_TIMES自增一,即处理次数加一
     * @param ids
     */
    void incrementDealTimes(List<Integer> ids);

    /**
     * 查询未完成重呼的任务数量
     * @param recallTimes   应该重呼次数
     * @return
     */
    Integer countUnCompleteRecall(@Param("recallTimes") Integer recallTimes,@Param("jobName") String jobName);

    /**
     * 查询一天的数据
     * @param date
     * @return
     */
    List<JobDetail> loadOneDayJobDetails(@Param("date") String date);

    /**
     * 查询待执行的短信任务
     * @param condition
     * @return
     */
    List<ShortMsgJobDetail> loadShortMsgJobDetails(JobDetailCondition condition);

    /**
     * 详情里短信发送状态统一更改为是
     * @param ids
     */
    void incrementSendShortMsg(List<Integer> ids);

    /**
     * 详情里短信发送状态统一更改为未回
     * @param jobName
     */
    void updateSendShortMsgStatus(@Param("jobName") String jobName);

    /**
     * DEAL_STATUS状态改为已外呼
     * @param ids
     */
    void incrementDealStatus(List<Integer> ids);

    /**
     * 根据任务名称统计任务详情的状态
     * @param jobName
     * @return
     */
    JobCountCallNum countCallNum(@Param("jobName") String jobName);

    /**
     * 根据jobName删除任务的信息
     * @param jobName
     * @return
     */
    int deleteByJobName(@Param("jobName") String jobName);

    /**
     * 根据外呼ID查询录音相关
     * @param id
     * @return
     */
    JobDetail selectJobName(Integer id);

    /**
     * 根据jobId查询相应的号码，有用
     * @param JobId
     * @return
     */
    String selectPassNumbyJobId(@Param("jobId") String JobId);
    /**
     * 根据jodid查询对应的JobDetail和BussinessData
     * @param JobId
     * @return
     */
    BussinessHallResult selectJobDetailAndBussinessData(@Param("jobId") String jobId);
    
}