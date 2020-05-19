package com.knowology.request;

import lombok.Data;

/**
 *
 *
 * @author xullent
 * @date 2020/3/10 14:54
 */
@Data
public class CallOutMonitorQuery {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 批次
     */
    private String batch;

    /**
     * 时间区间 今日-本周-本月-全部
     */
    private String timeInterval;
}
