package com.knowology.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "Z_STRATEGY")
public class StrategyDetail implements Serializable {
	private static final long serialVersionUID = 6347897382680762330L;

	@Id
    @Column(name = "ID")
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 重呼策略名称
     */
    @Column(name = "STRATEGY_NAME")
    private String strategyName;

    /**
     * 重呼间隔
     */
    @Column(name = "AGAIN_CALL_INTERVAL")
    private Integer againCallInterval;

    /**
     * 重呼次数
     */
    @Column(name = "CALL_TIMES")
    private Integer callTimes;

    /**
     * 失败原因
     */
    @Column(name = "FAILURE_CASE")
    private String failureCase;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

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
     * 获取重呼策略名称
     *
     * @return STRATEGY_NAME - 重呼策略名称
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * 设置重呼策略名称
     *
     * @param strategyName 重呼策略名称
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }

    /**
     * 获取重呼间隔
     *
     * @return AGAIN_CALL_INTERVAL - 重呼间隔
     */
    public Integer getAgainCallInterval() {
        return againCallInterval;
    }

    /**
     * 设置重呼间隔
     *
     * @param againCallInterval 重呼间隔
     */
    public void setAgainCallInterval(Integer againCallInterval) {
        this.againCallInterval = againCallInterval;
    }

    /**
     * 获取呼叫次数
     *
     * @return CALL_TIMES - 呼叫次数
     */
    public Integer getCallTimes() {
        return callTimes;
    }

    /**
     * 设置呼叫次数
     *
     * @param callTimes 呼叫次数
     */
    public void setCallTimes(Integer callTimes) {
        this.callTimes = callTimes;
    }

    /**
     * 获取失败原因
     *
     * @return FAILURE_CASE - 失败原因
     */
    public String getFailureCase() {
        return failureCase;
    }

    /**
     * 设置失败原因
     *
     * @param failureCase 失败原因
     */
    public void setFailureCase(String failureCase) {
        this.failureCase = failureCase == null ? null : failureCase.trim();
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建人
     *
     * @return CREATOR - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}