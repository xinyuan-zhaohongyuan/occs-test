package com.knowology.request;

import org.hibernate.validator.constraints.Length;

import com.knowology.valid.SearchCheck;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>JobDetail查询条件</p>
 *
 * @author : Conan
 * @date : 2019-08-02 15:12
 **/
@Data
@EqualsAndHashCode(callSuper=false)
public class JobDetailQuery extends BaseQuery{

    /**
     * 所属任务名
     */
    @Length(max = 20,groups = {SearchCheck.class},message = "任务名称长度不得超于20字符")
    private String jobName;

    /**
     * 电话号码
     */
    private String passiveNum;

    /**
     * 外呼时间区间 开始
     */
    private String startTime;

    /**
     * 外呼时间区间 结束
     */
    private String endTime;

    /**
     * 结果分类
     */
    private String resultType;

    /**
     * 呼叫状态
     */
    private String callStatus;

    /**
     * 交互轮次
     */
    private Integer rounds;

    /**
     * 通话时长(例如：0-59) 0
     */
    private String talkTime;

    /**
     * 挂断方
     */
    private String hangup;

    /**
     * 客户姓名
     */
    @Length(max = 10,groups = {SearchCheck.class},message = "客户姓名长度不得超于10字符")
    private String clientName;

    /**
     * 客户属性
     */
    private String clientAttribute;

    /**
     * 营业厅
     */
    private String businessHallName;

    /**
     * 任务id
     */
    private Integer jobId;
}
