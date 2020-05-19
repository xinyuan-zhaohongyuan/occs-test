package com.knowology.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 语音标注查询条件
 *
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class VoiceQuery extends BaseQuery {

    /**
     * 是否标注
     */
    private String labelStatus;
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 任务id
     */
    private Integer jobId;

    /**
     * 电话号码
     */
    private String passiveNum;
}
