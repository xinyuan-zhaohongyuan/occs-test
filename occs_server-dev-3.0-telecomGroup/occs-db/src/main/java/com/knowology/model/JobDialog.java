package com.knowology.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_JOB_DIALOG")
public class JobDialog extends BaseRowModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 外呼ID
     */
    @Column(name = "JOB_ID")
    @ExcelProperty(value = "JobId", index = 0)
    private String jobId;

    /**
     * 0-机器人;1-用户
     */
    @Column(name = "PERSION")
    @ExcelProperty(value = "客户标志", index = 1)
    private Integer persion;

    /**
     * 对话内容
     */
    @Column(name = "CONTENT")
    @ExcelProperty(value = "对话内容", index = 2)
    private String content;

    @Column(name = "CREATE_TIME")
    @ExcelProperty(value = "呼叫时间", index = 3)
    private Date createTime;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取外呼ID
     *
     * @return JOB_ID - 外呼ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 设置外呼ID
     *
     * @param jobId 外呼ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * 获取0-机器人;1-用户
     *
     * @return PERSION - 0-机器人;1-用户
     */
    public Integer getPersion() {
        return persion;
    }

    /**
     * 设置0-机器人;1-用户
     *
     * @param persion 0-机器人;1-用户
     */
    public void setPersion(Integer persion) {
        this.persion = persion;
    }

    /**
     * 获取对话内容
     *
     * @return CONTENT - 对话内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置对话内容
     *
     * @param content 对话内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}