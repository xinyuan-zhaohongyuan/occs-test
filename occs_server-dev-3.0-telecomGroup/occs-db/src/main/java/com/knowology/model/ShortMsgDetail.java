package com.knowology.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_SHORTMSG_DETAILS")
public class ShortMsgDetail extends BaseRowModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 短信任务ID
     */
    @Column(name = "SHORTMSG_ID")
    private Integer shortmsgId;

    /**
     * 短信任务名称
     */
    @Column(name = "SHORTMSG_NAME")
    private String shortmsgName;

    /**
     * 电话号
     */
    @ExcelProperty(value = "电话号码", index = 0)
    @Column(name = "TELENUM")
    private String telenum;

    /**
     * 发送短信时间
     */
    @ExcelProperty(value = "发送时间", index = 1)
    @Column(name = "SEND_TIME")
    private Date sendTime;

    /**
     * 回复短信时间
     */
    @ExcelProperty(value = "回复时间", index = 2)
    @Column(name = "RECEIVE_TIME")
    private Date receiveTime;

    /**
     * 回复内容
     */
    @ExcelProperty(value = "回复内容", index = 3)
    @Column(name = "RECEIVE_CONTENT")
    private String receiveContent;

    /**
     * 处理状态
     */
    @Column(name = "DEAL_STATUS")
    private String dealStatus;

    /**
     * UUID
     */
    @Column(name = "UUID")
    private String uuid;

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
     * 获取短信任务ID
     *
     * @return SHORTMSG_ID - 短信任务ID
     */
    public Integer getShortmsgId() {
        return shortmsgId;
    }

    /**
     * 设置短信任务ID
     *
     * @param shortmsgId 短信任务ID
     */
    public void setShortmsgId(Integer shortmsgId) {
        this.shortmsgId = shortmsgId;
    }

    /**
     * 获取短信任务名称
     *
     * @return SHORTMSG_NAME - 短信任务名称
     */
    public String getShortmsgName() {
        return shortmsgName;
    }

    /**
     * 设置短信任务名称
     *
     * @param shortmsgName 短信任务名称
     */
    public void setShortmsgName(String shortmsgName) {
        this.shortmsgName = shortmsgName == null ? null : shortmsgName.trim();
    }

    /**
     * 获取电话号
     *
     * @return TELENUM - 电话号
     */
    public String getTelenum() {
        return telenum;
    }

    /**
     * 设置电话号
     *
     * @param telenum 电话号
     */
    public void setTelenum(String telenum) {
        this.telenum = telenum == null ? null : telenum.trim();
    }

    /**
     * 获取发送短信时间
     *
     * @return SEND_TIME - 发送短信时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送短信时间
     *
     * @param sendTime 发送短信时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取回复短信时间
     *
     * @return RECEIVE_TIME - 回复短信时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置回复短信时间
     *
     * @param receiveTime 回复短信时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取回复内容
     *
     * @return RECEIVE_CONTENT - 回复内容
     */
    public String getReceiveContent() {
        return receiveContent;
    }

    /**
     * 设置回复内容
     *
     * @param receiveContent 回复内容
     */
    public void setReceiveContent(String receiveContent) {
        this.receiveContent = receiveContent == null ? null : receiveContent.trim();
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
}