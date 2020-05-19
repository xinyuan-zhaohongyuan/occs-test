package com.knowology.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 专项筛选 --条件
 *
 * @author xullent
 */
@Data
public class SpecialFilterCondition {

    /**
     * 办理完成时间--起始
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTimeBegin;

    /**
     * 办理完成时间--结束
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTimeEnd;

    /**
     * 数据入库时间--起始
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date inputTimeBegin;

    /**
     * 数据入库时间--结束
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date inputTimeEnd;

    /**
     * 省
     */
    private String province;

    /**
     * 地区
     */
    private String city;

    /**
     * 营业厅
     */
    private String businessHallName;

    /**
     * 业务类型
     */
    private String bussinessType;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 筛选比例
     */
    private Integer filterRatio;

    /**
     * 号码组名称
     */
    private String telenumGroupName;
}
