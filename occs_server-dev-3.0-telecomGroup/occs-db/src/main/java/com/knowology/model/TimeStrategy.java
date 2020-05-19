package com.knowology.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "Z_TIME_STRATEGY")
public class TimeStrategy implements Serializable {
	private static final long serialVersionUID = 6245560018043566391L;

	@Id
    @Column(name = "ID")
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 时间策略名称
     */
    @Column(name = "STRATEGY_NAME")
    private String strategyName;

    /**
     * 执行时间 JSONArray存放
     */
    @Column(name = "EXECUTE_TIME")
    private String executeTime;

    /**
     * 总时间段(开始时间段-结束时间段)
     */
    private String timeQuantum;

    /**
     * 重复星期
     */
    @Column(name = "REPEAT_WEEK")
    private String repeatWeek;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

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
     * 获取时间策略名称
     *
     * @return STRATEGY_NAME - 时间策略名称
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * 设置时间策略名称
     *
     * @param strategyName 时间策略名称
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }


    /**
     * 获取重复星期
     *
     * @return REPEAT_WEEK - 重复星期
     */
    public String getRepeatWeek() {
        return repeatWeek;
    }

    /**
     * 设置重复星期
     *
     * @param repeatWeek 重复星期
     */
    public void setRepeatWeek(String repeatWeek) {
        this.repeatWeek = repeatWeek == null ? null : repeatWeek.trim();
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

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }
}