package com.knowology.service;

import com.knowology.po.RealtimeExcute;

import java.util.List;
import java.util.Map;

/**
 * 外呼情况监控
 * @author xullent
 */
public interface CallOutMonitorService {
    /**
     * 任务实时执行统计
     * @param jobName
     * @return
     */
    RealtimeExcute listRealtimeExcute(String jobName);
    /**
     * 呼叫状态统计
     * @param jobName
     * @return
     */
    List<Map<String,String>> listCallStatus(String jobName);

    /**
     * 通话时长统计
     * @param jobName
     * @return
     */
    List<Map<String,String>> listTalkTime(String jobName);

    /**
     * 交互轮次统计
     * @param jobName
     * @return
     */
    List<Map<String,String>> listRounds(String jobName);

    /**
     * 挂断方统计
     * @param jobName
     * @return
     */
    List<Map<String,String>> listHangup(String jobName);
}
