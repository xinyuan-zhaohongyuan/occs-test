package com.knowology.model;

import javax.persistence.*;

@Table(name = "Z_QUOTA")
public class Quota {
    /**
     * 配额缓存key
     */
    @Id
    @Column(name = "QUOTA_KEY")
    private String quotaKey;

    /**
     * 任务名
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 配额
     */
    @Column(name = "QUOTA")
    private String quota;

    /**
     * 获取配额缓存key
     *
     * @return QUOTA_KEY - 配额缓存key
     */
    public String getQuotaKey() {
        return quotaKey;
    }

    /**
     * 设置配额缓存key
     *
     * @param quotaKey 配额缓存key
     */
    public void setQuotaKey(String quotaKey) {
        this.quotaKey = quotaKey == null ? null : quotaKey.trim();
    }

    /**
     * 获取任务名
     *
     * @return JOB_NAME - 任务名
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名
     *
     * @param jobName 任务名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * 获取配额
     *
     * @return QUOTA - 配额
     */
    public String getQuota() {
        return quota;
    }

    /**
     * 设置配额
     *
     * @param quota 配额
     */
    public void setQuota(String quota) {
        this.quota = quota == null ? null : quota.trim();
    }
}