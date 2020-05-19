package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_JOB")
public class Job {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 号码组名称
     */
    @Column(name = "TELENUM_GROUP_NAME")
    private String telenumGroupName;

    /**
     * 场景名称
     */
    @Column(name = "SCENE_NAME")
    private String sceneName;

    /**
     * 放音类型
     */
    @Column(name = "PLAY_MODE")
    private String playMode;

    /**
     * cron表达式
     */
    @Column(name = "CRON_EXPRESSION")
    private String cronExpression;

    /**
     * 任务状态
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 任务描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 重呼策略名称
     */
    @Column(name = "STRATEGY_NAME")
    private String strategyName;

    /**
     * 时间策略名称
     */
    @Column(name = "TIME_STRATEGY_NAME")
    private String timeStrategyName;

    /**
     * 已呼
     */
    @Column(name = "COMPLETE")
    private Integer complete;

    /**
     * 成功
     */
    @Column(name = "SUCCESS")
    private Integer success;

    /**
     * 总数
     */
    @Column(name = "TOTAL")
    private Integer total;

    /**
     * 可以呼叫
     */
    @Column(name = "READY")
    private Integer ready;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_USER")
    private String updateUser;

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
     * 短信模板名称
     */
    @Column(name = "SHORTMSG_MODEL_NAME")
    private String shortmsgModelName;

    /**
     * 等待回复时间--切换成秒级
     */
    @Column(name = "WAIT_REPLY_TIME")
    private Integer waitReplyTime;

    /**
     * 短信是否发送完毕 0-否 1-是
     */
    @Column(name = "SEND_SHORTMSG")
    private Integer sendShortmsg;

    /**
     * 是否自动外呼，默认0-非自动，1-自动
     */
    @Column(name = "IS_AUTO")
    private Integer isAuto;

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
     * 获取任务名称
     *
     * @return JOB_NAME - 任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名称
     *
     * @param jobName 任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * 获取号码组名称
     *
     * @return TELENUM_GROUP_NAME - 号码组名称
     */
    public String getTelenumGroupName() {
        return telenumGroupName;
    }

    /**
     * 设置号码组名称
     *
     * @param telenumGroupName 号码组名称
     */
    public void setTelenumGroupName(String telenumGroupName) {
        this.telenumGroupName = telenumGroupName == null ? null : telenumGroupName.trim();
    }

    /**
     * 获取场景名称
     *
     * @return SCENE_NAME - 场景名称
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * 设置场景名称
     *
     * @param sceneName 场景名称
     */
    public void setSceneName(String sceneName) {
        this.sceneName = sceneName == null ? null : sceneName.trim();
    }

    /**
     * 获取放音类型
     *
     * @return PLAY_MODE - 放音类型
     */
    public String getPlayMode() {
        return playMode;
    }

    /**
     * 设置放音类型
     *
     * @param playMode 放音类型
     */
    public void setPlayMode(String playMode) {
        this.playMode = playMode == null ? null : playMode.trim();
    }

    /**
     * 获取cron表达式
     *
     * @return CRON_EXPRESSION - cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置cron表达式
     *
     * @param cronExpression cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    /**
     * 获取任务状态
     *
     * @return STATUS - 任务状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置任务状态
     *
     * @param status 任务状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取任务描述
     *
     * @return DESCRIPTION - 任务描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置任务描述
     *
     * @param description 任务描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
     * 获取时间策略名称
     *
     * @return TIME_STRATEGY_NAME - 时间策略名称
     */
    public String getTimeStrategyName() {
        return timeStrategyName;
    }

    /**
     * 设置时间策略名称
     *
     * @param timeStrategyName 时间策略名称
     */
    public void setTimeStrategyName(String timeStrategyName) {
        this.timeStrategyName = timeStrategyName == null ? null : timeStrategyName.trim();
    }

    /**
     * 获取已呼
     *
     * @return COMPLETE - 已呼
     */
    public Integer getComplete() {
        return complete;
    }

    /**
     * 设置已呼
     *
     * @param complete 已呼
     */
    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    /**
     * 获取成功
     *
     * @return SUCCESS - 成功
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * 设置成功
     *
     * @param success 成功
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * 获取总数
     *
     * @return TOTAL - 总数
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 设置总数
     *
     * @param total 总数
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取可以呼叫
     *
     * @return READY - 可以呼叫
     */
    public Integer getReady() {
        return ready;
    }

    /**
     * 设置可以呼叫
     *
     * @param ready 可以呼叫
     */
    public void setReady(Integer ready) {
        this.ready = ready;
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

    /**
     * 获取更新人
     *
     * @return UPDATE_USER - 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人
     *
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
     * 获取短信模板名称
     *
     * @return SHORTMSG_MODEL_NAME - 短信模板名称
     */
    public String getShortmsgModelName() {
        return shortmsgModelName;
    }

    /**
     * 设置短信模板名称
     *
     * @param shortmsgModelName 短信模板名称
     */
    public void setShortmsgModelName(String shortmsgModelName) {
        this.shortmsgModelName = shortmsgModelName == null ? null : shortmsgModelName.trim();
    }

    /**
     * 获取等待回复时间--切换成秒级
     *
     * @return WAIT_REPLY_TIME - 等待回复时间--切换成秒级
     */
    public Integer getWaitReplyTime() {
        return waitReplyTime;
    }

    /**
     * 设置等待回复时间--切换成秒级
     *
     * @param waitReplyTime 等待回复时间--切换成秒级
     */
    public void setWaitReplyTime(Integer waitReplyTime) {
        this.waitReplyTime = waitReplyTime;
    }

    /**
     * 获取短信是否发送完毕 0-否 1-是
     *
     * @return SEND_SHORTMSG - 短信是否发送完毕 0-否 1-是
     */
    public Integer getSendShortmsg() {
        return sendShortmsg;
    }

    /**
     * 设置短信是否发送完毕 0-否 1-是
     *
     * @param sendShortmsg 短信是否发送完毕 0-否 1-是
     */
    public void setSendShortmsg(Integer sendShortmsg) {
        this.sendShortmsg = sendShortmsg;
    }

    /**
     * 获取是否自动外呼，默认0-非自动，1-自动
     *
     * @return IS_AUTO - 是否自动外呼，默认0-非自动，1-自动
     */
    public Integer getIsAuto() {
        return isAuto;
    }

    /**
     * 设置是否自动外呼，默认0-非自动，1-自动
     *
     * @param isAuto 是否自动外呼，默认0-非自动，1-自动
     */
    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }
}