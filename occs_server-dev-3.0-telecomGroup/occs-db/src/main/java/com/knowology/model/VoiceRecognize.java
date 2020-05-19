package com.knowology.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_VOICE_RECOGNIZE")
public class VoiceRecognize extends BaseRowModel {
    @Id
    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    @ExcelProperty(value = "工单ID",  index = 0)
    private Integer id;

    /**
     * 外呼ID
     */
    @Column(name = "JOB_ID")
    @ExcelProperty(value = "外呼ID",  index = 7)
    private Integer jobId;

    /**
     * 识别结果
     */
    @Column(name = "RECOGNIZE_RESULT")
    @ExcelProperty(value = "识别结果",  index = 6)
    private String recognizeResult;

    /**
     * 呼叫时间
     */
    @Column(name = "CALL_TIME")
    private Date callTime;

    /**
     * 录音开始时间
     */
    @Column(name = "BEGIN_TIME")
    private String beginTime;

    /**
     * 录音结束时间
     */
    @Column(name = "END_TIME")
    private String endTime;

    /**
     * 标注状态
     */
    @Column(name = "LABEL_STATUS")
    private String labelStatus;

    /**
     * 录音地址
     */
    @Column(name = "URL")
    private String url;

    /**
     * 录音分类
     */
    @Column(name = "VOICE_TYPE")
    @ExcelProperty(value = "录音分类",  index = 2)
    private String voiceType;

    /**
     * 标注类型
     */
    @Column(name = "LABEL_TYPE")
    @ExcelProperty(value = "是否正确",  index = 4)
    private String labelType;

    /**
     * 人工纠正
     */
    @Column(name = "MANUAL_CORRECT")
    @ExcelProperty(value = "人工纠正",  index = 5)
    private String manualCorrect;

    /**
     * 标注时间
     */
    @Column(name = "LABEL_TIME")
    @ExcelProperty(value = "标注时间",  index = 9)
    private Date labelTime;

    /**
     * 标注人
     */
    @Column(name = "LABEL_PERSION")
    @ExcelProperty(value = "标注人",  index = 8)
    private String labelPersion;

    /**
     * 录音质量
     */
    @Column(name = "VOICE_QUALITY")
    @ExcelProperty(value = "发音质量",  index = 1)
    private String voiceQuality;

    /**
     * 工单类型
     */
    @Column(name = "ORDER_TYPE")
    @ExcelProperty(value = "派单类型",  index = 3)
    private String orderType;

    /**
     * 呼叫号码
     */
    @Column(name = "PASSIVE_NUM")
    private String passiveNum;

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
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 设置外呼ID
     *
     * @param jobId 外呼ID
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取识别结果
     *
     * @return RECOGNIZE_RESULT - 识别结果
     */
    public String getRecognizeResult() {
        return recognizeResult;
    }

    /**
     * 设置识别结果
     *
     * @param recognizeResult 识别结果
     */
    public void setRecognizeResult(String recognizeResult) {
        this.recognizeResult = recognizeResult == null ? null : recognizeResult.trim();
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
     * 获取录音开始时间
     *
     * @return BEGIN_TIME - 录音开始时间
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置录音开始时间
     *
     * @param beginTime 录音开始时间
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    /**
     * 获取录音结束时间
     *
     * @return END_TIME - 录音结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置录音结束时间
     *
     * @param endTime 录音结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    /**
     * 获取标注状态
     *
     * @return LABEL_STATUS - 标注状态
     */
    public String getLabelStatus() {
        return labelStatus;
    }

    /**
     * 设置标注状态
     *
     * @param labelStatus 标注状态
     */
    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus == null ? null : labelStatus.trim();
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
     * 获取录音分类
     *
     * @return VOICE_TYPE - 录音分类
     */
    public String getVoiceType() {
        return voiceType;
    }

    /**
     * 设置录音分类
     *
     * @param voiceType 录音分类
     */
    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType == null ? null : voiceType.trim();
    }

    /**
     * 获取标注类型
     *
     * @return LABEL_TYPE - 标注类型
     */
    public String getLabelType() {
        return labelType;
    }

    /**
     * 设置标注类型
     *
     * @param labelType 标注类型
     */
    public void setLabelType(String labelType) {
        this.labelType = labelType == null ? null : labelType.trim();
    }

    /**
     * 获取人工纠正
     *
     * @return MANUAL_CORRECT - 人工纠正
     */
    public String getManualCorrect() {
        return manualCorrect;
    }

    /**
     * 设置人工纠正
     *
     * @param manualCorrect 人工纠正
     */
    public void setManualCorrect(String manualCorrect) {
        this.manualCorrect = manualCorrect == null ? null : manualCorrect.trim();
    }

    /**
     * 获取标注时间
     *
     * @return LABEL_TIME - 标注时间
     */
    public Date getLabelTime() {
        return labelTime;
    }

    /**
     * 设置标注时间
     *
     * @param labelTime 标注时间
     */
    public void setLabelTime(Date labelTime) {
        this.labelTime = labelTime;
    }

    /**
     * 获取标注人
     *
     * @return LABEL_PERSION - 标注人
     */
    public String getLabelPersion() {
        return labelPersion;
    }

    /**
     * 设置标注人
     *
     * @param labelPersion 标注人
     */
    public void setLabelPersion(String labelPersion) {
        this.labelPersion = labelPersion == null ? null : labelPersion.trim();
    }

    /**
     * 获取录音质量
     *
     * @return VOICE_QUALITY - 录音质量
     */
    public String getVoiceQuality() {
        return voiceQuality;
    }

    /**
     * 设置录音质量
     *
     * @param voiceQuality 录音质量
     */
    public void setVoiceQuality(String voiceQuality) {
        this.voiceQuality = voiceQuality == null ? null : voiceQuality.trim();
    }

    /**
     * 获取工单类型
     *
     * @return ORDER_TYPE - 工单类型
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 设置工单类型
     *
     * @param orderType 工单类型
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getPassiveNum() {
        return passiveNum;
    }

    public void setPassiveNum(String passiveNum) {
        this.passiveNum = passiveNum;
    }
}