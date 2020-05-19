package com.knowology.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "Z_JOB")
public class Job implements Serializable {
    private static final long serialVersionUID = -4958221862498832583L;
    //外呼处理状态
    public static final String WAIT_CALL = "未外呼";
    public static final String ALREADY_CALL = "已外呼";

    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    public static final String STRATEGY_KEY = "STRATEGY_KEY";
    public static final String TIME_STRATEGY_KEY = "TIME_STRATEGY_KEY";

    public enum ScheduleStatus {
        /**
         * 待执行
         */
        WAIT("待执行"),
        /**
         * 运行中
         */
        RUN("执行中"),
        /**
         * 暂停
         */
        PAUSE("暂停中"),

        /**
         * 已完成
         */
        FINISH("已完成");

        private String value;

        private ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Id
    @Column(name = "ID")
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 任务描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 号码组名称
     */
    @Column(name = "TELENUM_GROUP_NAME")
    private String telenumGroupName;

    /**
     * 多个号码组名称
     */
    private List<String> telenumGroupNameList;

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
     * 总数
     */
    @Column(name = "TOTAL")
    private Integer total;

    /**
     * 已完成
     */
    @Column(name = "COMPLETE")
    private Integer complete;

    /**
     * 成功
     */
    @Column(name = "SUCCESS")
    private Integer success;

    /**
     * 短信模板
     */
    @Column(name = "SHORTMSG_MODEL_NAME")
    private String shortMsgModelName;

    /**
     * 等待回复时间
     */
    @Column(name = "WAIT_REPLY_TIME")
    private Integer waitReplyTime;

    /**
     * 可呼数量
     */
    @Column(name = "READY")
    private Integer ready;

    /**
     * 短信是否发送完毕
     */
    @Column(name = "SEND_SHORTMSG")
    private Integer sendShortMsg;

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
     * 获取总数
     *
     * @return COMPLETE - 总数
     */
    public Integer getComplete() {
        return complete;
    }

    /**
     * 设置总数
     *
     * @param complete 总数
     */
    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    /**
     * 获取已呼
     *
     * @return SUCCESS - 已呼
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * 设置已呼
     *
     * @param success 已呼
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getShortMsgModelName() {
        return shortMsgModelName;
    }

    public Integer getWaitReplyTime() {
        return waitReplyTime;
    }

    public void setShortMsgModelName(String shortMsgModelName) {
        this.shortMsgModelName = shortMsgModelName;
    }

    public void setWaitReplyTime(Integer waitReplyTime) {
        this.waitReplyTime = waitReplyTime;
    }

    public Integer getReady() {
        return ready;
    }

    public void setReady(Integer ready) {
        this.ready = ready;
    }

    public Integer getSendShortMsg() {
        return sendShortMsg;
    }

    public void setSendShortMsg(Integer sendShortMsg) {
        this.sendShortMsg = sendShortMsg;
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

    public List<String> getTelenumGroupNameList() {
        return telenumGroupNameList;
    }

    public void setTelenumGroupNameList(List<String> telenumGroupNameList) {
        this.telenumGroupNameList = telenumGroupNameList;
    }
}