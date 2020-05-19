package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_JOB_DETAILS_TASK")
public class    JobDetailTask {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 呼叫时间
     */
    @Column(name = "CALL_TIME")
    private Date callTime;

    /**
     * 呼叫状态
     */
    @Column(name = "CALL_STATUS")
    private String callStatus;

    /**
     * 被叫号码
     */
    @Column(name = "PASSIVE_NUM")
    private String passiveNum;

    /**
     * 关联任务名称
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 场景名
     */
    @Column(name = "SCENE")
    private String scene;

    /**
     * 处理状态
     */
    @Column(name = "DEAL_STATUS")
    private String dealStatus;

    /**
     * 通话时长
     */
    @Column(name = "TALK_TIME")
    private Integer talkTime;

    /**
     * 交互轮次
     */
    @Column(name = "ROUNDS")
    private Integer rounds;

    /**
     * 挂断方
     */
    @Column(name = "HANGUP")
    private String hangup;

    /**
     * 录音地址
     */
    @Column(name = "URL")
    private String url;

    /**
     * 处理次数
     */
    @Column(name = "DEAL_TIMES")
    private Integer dealTimes;

    /**
     * 结果分类
     */
    @Column(name = "RESULT_TYPE")
    private String resultType;

    /**
     * 客户名称
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     * 营业厅
     */
    @Column(name = "BUSINESS_HALL_NAME")
    private String businessHallName;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 节点标签
     */
    @Column(name = "NODE")
    private String node;

    /**
     * 整体满意度
     */
    @Column(name = "OVERALL")
    private Integer overall;

    /**
     * 满意原因
     */
    @Column(name = "SATIS_REASION")
    private String satisReasion;

    /**
     * 满意原因(内容)
     */
    @Column(name = "SATIS_REASION_TXT")
    private String satisReasionTxt;

    /**
     * 不满意原因
     */
    @Column(name = "NOSATIS_REASION")
    private String nosatisReasion;

    /**
     * 不满意原因(内容)
     */
    @Column(name = "NOSATIS_REASION_TXT")
    private String nosatisReasionTxt;

    /**
     * 等候时长不满意
     */
    @Column(name = "WAIT_TIME_REASION")
    private String waitTimeReasion;

    /**
     * 等候时长不满意(内容)
     */
    @Column(name = "WAIT_TIME_REASION_TXT")
    private String waitTimeReasionTxt;

    /**
     * 服务态度不满意
     */
    @Column(name = "SERVICE_REASION")
    private String serviceReasion;

    /**
     * 服务态度不满意(内容)
     */
    @Column(name = "SERVICE_REASION_TXT")
    private String serviceReasionTxt;

    /**
     * 业务技能不满意
     */
    @Column(name = "SKILL_REASION")
    private String skillReasion;

    /**
     * 业务技能不满意(内容)
     */
    @Column(name = "SKILL_REASION_TXT")
    private String skillReasionTxt;

    /**
     * 服务环境不满意
     */
    @Column(name = "HALL_ENV_REASION")
    private String hallEnvReasion;

    /**
     * 服务环境不满意(内容)
     */
    @Column(name = "HALL_ENV_REASION_TXT")
    private String hallEnvReasionTxt;

    @Column(name = "PROVINCE")
    private String province;

    /**
     * 短信预约结果
     */
    @Column(name = "APPOINT_RESULT")
    private String appointResult;

    /**
     * 短信发送时间
     */
    @Column(name = "SHORTMSG_SEND_TIME")
    private Date shortmsgSendTime;

    /**
     * 短信接收时间
     */
    @Column(name = "SHORTMSG_RECEIVE_TIME")
    private Date shortmsgReceiveTime;

    /**
     * 是否发送短信
     */
    @Column(name = "SEND_SHORTMSG")
    private Integer sendShortmsg;

    /**
     * UUID
     */
    @Column(name = "UUID")
    private String uuid;

    /**
     * 业务类型
     */
    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    /**
     * 服务类型
     */
    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    /**
     * 通话内容
     */
    @Column(name = "CONTENT")
    private String content;

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
     * 获取呼叫时间
     *
     * @return CALL_TIME - 呼叫时间
     */
    public Date getCallTime() {
        return callTime;
    }

    /**
     * 设置呼叫时间
     *
     * @param callTime 呼叫时间
     */
    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    /**
     * 获取呼叫状态
     *
     * @return CALL_STATUS - 呼叫状态
     */
    public String getCallStatus() {
        return callStatus;
    }

    /**
     * 设置呼叫状态
     *
     * @param callStatus 呼叫状态
     */
    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus == null ? null : callStatus.trim();
    }

    /**
     * 获取被叫号码
     *
     * @return PASSIVE_NUM - 被叫号码
     */
    public String getPassiveNum() {
        return passiveNum;
    }

    /**
     * 设置被叫号码
     *
     * @param passiveNum 被叫号码
     */
    public void setPassiveNum(String passiveNum) {
        this.passiveNum = passiveNum == null ? null : passiveNum.trim();
    }

    /**
     * 获取关联任务名称
     *
     * @return JOB_NAME - 关联任务名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置关联任务名称
     *
     * @param jobName 关联任务名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * 获取场景名
     *
     * @return SCENE - 场景名
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置场景名
     *
     * @param scene 场景名
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    /**
     * 获取处理状态
     *
     * @return DEAL_STATUS - 处理状态
     */
    public String getDealStatus() {
        return dealStatus;
    }

    /**
     * 设置处理状态
     *
     * @param dealStatus 处理状态
     */
    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus == null ? null : dealStatus.trim();
    }

    /**
     * 获取通话时长
     *
     * @return TALK_TIME - 通话时长
     */
    public Integer getTalkTime() {
        return talkTime;
    }

    /**
     * 设置通话时长
     *
     * @param talkTime 通话时长
     */
    public void setTalkTime(Integer talkTime) {
        this.talkTime = talkTime;
    }

    /**
     * 获取交互轮次
     *
     * @return ROUNDS - 交互轮次
     */
    public Integer getRounds() {
        return rounds;
    }

    /**
     * 设置交互轮次
     *
     * @param rounds 交互轮次
     */
    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    /**
     * 获取挂断方
     *
     * @return HANGUP - 挂断方
     */
    public String getHangup() {
        return hangup;
    }

    /**
     * 设置挂断方
     *
     * @param hangup 挂断方
     */
    public void setHangup(String hangup) {
        this.hangup = hangup == null ? null : hangup.trim();
    }

    /**
     * 获取录音地址
     *
     * @return URL - 录音地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置录音地址
     *
     * @param url 录音地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取处理次数
     *
     * @return DEAL_TIMES - 处理次数
     */
    public Integer getDealTimes() {
        return dealTimes;
    }

    /**
     * 设置处理次数
     *
     * @param dealTimes 处理次数
     */
    public void setDealTimes(Integer dealTimes) {
        this.dealTimes = dealTimes;
    }

    /**
     * 获取结果分类
     *
     * @return RESULT_TYPE - 结果分类
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * 设置结果分类
     *
     * @param resultType 结果分类
     */
    public void setResultType(String resultType) {
        this.resultType = resultType == null ? null : resultType.trim();
    }

    /**
     * 获取客户名称
     *
     * @return CLIENT_NAME - 客户名称
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置客户名称
     *
     * @param clientName 客户名称
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * 获取营业厅
     *
     * @return BUSINESS_HALL_NAME - 营业厅
     */
    public String getBusinessHallName() {
        return businessHallName;
    }

    /**
     * 设置营业厅
     *
     * @param businessHallName 营业厅
     */
    public void setBusinessHallName(String businessHallName) {
        this.businessHallName = businessHallName == null ? null : businessHallName.trim();
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
     * 获取节点标签
     *
     * @return NODE - 节点标签
     */
    public String getNode() {
        return node;
    }

    /**
     * 设置节点标签
     *
     * @param node 节点标签
     */
    public void setNode(String node) {
        this.node = node == null ? null : node.trim();
    }

    /**
     * 获取整体满意度
     *
     * @return OVERALL - 整体满意度
     */
    public Integer getOverall() {
        return overall;
    }

    /**
     * 设置整体满意度
     *
     * @param overall 整体满意度
     */
    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    /**
     * 获取满意原因
     *
     * @return SATIS_REASION - 满意原因
     */
    public String getSatisReasion() {
        return satisReasion;
    }

    /**
     * 设置满意原因
     *
     * @param satisReasion 满意原因
     */
    public void setSatisReasion(String satisReasion) {
        this.satisReasion = satisReasion == null ? null : satisReasion.trim();
    }

    /**
     * 获取满意原因(内容)
     *
     * @return SATIS_REASION_TXT - 满意原因(内容)
     */
    public String getSatisReasionTxt() {
        return satisReasionTxt;
    }

    /**
     * 设置满意原因(内容)
     *
     * @param satisReasionTxt 满意原因(内容)
     */
    public void setSatisReasionTxt(String satisReasionTxt) {
        this.satisReasionTxt = satisReasionTxt == null ? null : satisReasionTxt.trim();
    }

    /**
     * 获取不满意原因
     *
     * @return NOSATIS_REASION - 不满意原因
     */
    public String getNosatisReasion() {
        return nosatisReasion;
    }

    /**
     * 设置不满意原因
     *
     * @param nosatisReasion 不满意原因
     */
    public void setNosatisReasion(String nosatisReasion) {
        this.nosatisReasion = nosatisReasion == null ? null : nosatisReasion.trim();
    }

    /**
     * 获取不满意原因(内容)
     *
     * @return NOSATIS_REASION_TXT - 不满意原因(内容)
     */
    public String getNosatisReasionTxt() {
        return nosatisReasionTxt;
    }

    /**
     * 设置不满意原因(内容)
     *
     * @param nosatisReasionTxt 不满意原因(内容)
     */
    public void setNosatisReasionTxt(String nosatisReasionTxt) {
        this.nosatisReasionTxt = nosatisReasionTxt == null ? null : nosatisReasionTxt.trim();
    }

    /**
     * 获取等候时长不满意
     *
     * @return WAIT_TIME_REASION - 等候时长不满意
     */
    public String getWaitTimeReasion() {
        return waitTimeReasion;
    }

    /**
     * 设置等候时长不满意
     *
     * @param waitTimeReasion 等候时长不满意
     */
    public void setWaitTimeReasion(String waitTimeReasion) {
        this.waitTimeReasion = waitTimeReasion == null ? null : waitTimeReasion.trim();
    }

    /**
     * 获取等候时长不满意(内容)
     *
     * @return WAIT_TIME_REASION_TXT - 等候时长不满意(内容)
     */
    public String getWaitTimeReasionTxt() {
        return waitTimeReasionTxt;
    }

    /**
     * 设置等候时长不满意(内容)
     *
     * @param waitTimeReasionTxt 等候时长不满意(内容)
     */
    public void setWaitTimeReasionTxt(String waitTimeReasionTxt) {
        this.waitTimeReasionTxt = waitTimeReasionTxt == null ? null : waitTimeReasionTxt.trim();
    }

    /**
     * 获取服务态度不满意
     *
     * @return SERVICE_REASION - 服务态度不满意
     */
    public String getServiceReasion() {
        return serviceReasion;
    }

    /**
     * 设置服务态度不满意
     *
     * @param serviceReasion 服务态度不满意
     */
    public void setServiceReasion(String serviceReasion) {
        this.serviceReasion = serviceReasion == null ? null : serviceReasion.trim();
    }

    /**
     * 获取服务态度不满意(内容)
     *
     * @return SERVICE_REASION_TXT - 服务态度不满意(内容)
     */
    public String getServiceReasionTxt() {
        return serviceReasionTxt;
    }

    /**
     * 设置服务态度不满意(内容)
     *
     * @param serviceReasionTxt 服务态度不满意(内容)
     */
    public void setServiceReasionTxt(String serviceReasionTxt) {
        this.serviceReasionTxt = serviceReasionTxt == null ? null : serviceReasionTxt.trim();
    }

    /**
     * 获取业务技能不满意
     *
     * @return SKILL_REASION - 业务技能不满意
     */
    public String getSkillReasion() {
        return skillReasion;
    }

    /**
     * 设置业务技能不满意
     *
     * @param skillReasion 业务技能不满意
     */
    public void setSkillReasion(String skillReasion) {
        this.skillReasion = skillReasion == null ? null : skillReasion.trim();
    }

    /**
     * 获取业务技能不满意(内容)
     *
     * @return SKILL_REASION_TXT - 业务技能不满意(内容)
     */
    public String getSkillReasionTxt() {
        return skillReasionTxt;
    }

    /**
     * 设置业务技能不满意(内容)
     *
     * @param skillReasionTxt 业务技能不满意(内容)
     */
    public void setSkillReasionTxt(String skillReasionTxt) {
        this.skillReasionTxt = skillReasionTxt == null ? null : skillReasionTxt.trim();
    }

    /**
     * 获取服务环境不满意
     *
     * @return HALL_ENV_REASION - 服务环境不满意
     */
    public String getHallEnvReasion() {
        return hallEnvReasion;
    }

    /**
     * 设置服务环境不满意
     *
     * @param hallEnvReasion 服务环境不满意
     */
    public void setHallEnvReasion(String hallEnvReasion) {
        this.hallEnvReasion = hallEnvReasion == null ? null : hallEnvReasion.trim();
    }

    /**
     * 获取服务环境不满意(内容)
     *
     * @return HALL_ENV_REASION_TXT - 服务环境不满意(内容)
     */
    public String getHallEnvReasionTxt() {
        return hallEnvReasionTxt;
    }

    /**
     * 设置服务环境不满意(内容)
     *
     * @param hallEnvReasionTxt 服务环境不满意(内容)
     */
    public void setHallEnvReasionTxt(String hallEnvReasionTxt) {
        this.hallEnvReasionTxt = hallEnvReasionTxt == null ? null : hallEnvReasionTxt.trim();
    }

    /**
     * @return PROVINCE
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取短信预约结果
     *
     * @return APPOINT_RESULT - 短信预约结果
     */
    public String getAppointResult() {
        return appointResult;
    }

    /**
     * 设置短信预约结果
     *
     * @param appointResult 短信预约结果
     */
    public void setAppointResult(String appointResult) {
        this.appointResult = appointResult == null ? null : appointResult.trim();
    }

    /**
     * 获取短信发送时间
     *
     * @return SHORTMSG_SEND_TIME - 短信发送时间
     */
    public Date getShortmsgSendTime() {
        return shortmsgSendTime;
    }

    /**
     * 设置短信发送时间
     *
     * @param shortmsgSendTime 短信发送时间
     */
    public void setShortmsgSendTime(Date shortmsgSendTime) {
        this.shortmsgSendTime = shortmsgSendTime;
    }

    /**
     * 获取短信接收时间
     *
     * @return SHORTMSG_RECEIVE_TIME - 短信接收时间
     */
    public Date getShortmsgReceiveTime() {
        return shortmsgReceiveTime;
    }

    /**
     * 设置短信接收时间
     *
     * @param shortmsgReceiveTime 短信接收时间
     */
    public void setShortmsgReceiveTime(Date shortmsgReceiveTime) {
        this.shortmsgReceiveTime = shortmsgReceiveTime;
    }

    /**
     * 获取是否发送短信
     *
     * @return SEND_SHORTMSG - 是否发送短信
     */
    public Integer getSendShortmsg() {
        return sendShortmsg;
    }

    /**
     * 设置是否发送短信
     *
     * @param sendShortmsg 是否发送短信
     */
    public void setSendShortmsg(Integer sendShortmsg) {
        this.sendShortmsg = sendShortmsg;
    }

    /**
     * 获取UUID
     *
     * @return UUID - UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置UUID
     *
     * @param uuid UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 获取业务类型
     *
     * @return BUSINESS_TYPE - 业务类型
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置业务类型
     *
     * @param businessType 业务类型
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    /**
     * 获取服务类型
     *
     * @return SERVICE_TYPE - 服务类型
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 设置服务类型
     *
     * @param serviceType 服务类型
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    /**
     * 获取通话内容
     *
     * @return CONTENT - 通话内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置通话内容
     *
     * @param content 通话内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}