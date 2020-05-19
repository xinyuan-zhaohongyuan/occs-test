package com.knowology.dao;

import com.knowology.po.*;
import org.apache.ibatis.annotations.Param;

public interface CallOutMonitorMapper {

    /**
     * 任务实时执行情况
     * @param jobName
     * @return
     */
    RealtimeExcute realtimeExcuteResult(@Param("jobName") String jobName);

    /**
     * 呼叫状态统计
     * @param jobName
     * @return
     */
    CallStatus listCallStatusResult(@Param("jobName") String jobName);

    /**
     * 通话时长统计
     * @param jobName
     * @return
     */
    TalkTime listTalkTimeResult(@Param("jobName") String jobName);

    /**
     * 交互轮次统计
     * @param jobName
     * @return
     */
    Rounds listRoundsResult(@Param("jobName") String jobName);

    /**
     * 挂断方统计
     * @param jobName
     * @return
     */
    Hangup listHangupResult(@Param("jobName") String jobName);
}