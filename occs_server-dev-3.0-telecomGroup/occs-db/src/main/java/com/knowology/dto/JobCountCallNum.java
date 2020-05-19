package com.knowology.dto;

import lombok.Data;

/**
 * 呼出任务数量的统计
 */
@Data
public class JobCountCallNum {
    /**
     * 任务名
     */
    private String jobName;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 已完成呼叫次数
     */
    private Integer complete;

    /**
     * 待呼叫次数
     */
    private Integer ready;
    /**
     * 呼叫成功次数
     */
    private Integer success;
}
