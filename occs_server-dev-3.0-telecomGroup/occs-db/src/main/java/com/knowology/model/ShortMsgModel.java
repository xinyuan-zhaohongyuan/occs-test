package com.knowology.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Z_SHORTMSG")
public class ShortMsgModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 短信模板名称
     */
    @Column(name = "SHORTMSG_NAME")
    private String shortmsgName;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 短信内容
     */
    @Column(name = "SHORTMSG_CONTENT")
    private String shortmsgContent;

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
     * 获取短信模板名称
     *
     * @return SHORTMSG_NAME - 短信模板名称
     */
    public String getShortmsgName() {
        return shortmsgName;
    }

    /**
     * 设置短信模板名称
     *
     * @param shortmsgName 短信模板名称
     */
    public void setShortmsgName(String shortmsgName) {
        this.shortmsgName = shortmsgName == null ? null : shortmsgName.trim();
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
     * 获取短信内容
     *
     * @return SHORTMSG_CONTENT - 短信内容
     */
    public String getShortmsgContent() {
        return shortmsgContent;
    }

    /**
     * 设置短信内容
     *
     * @param shortmsgContent 短信内容
     */
    public void setShortmsgContent(String shortmsgContent) {
        this.shortmsgContent = shortmsgContent == null ? null : shortmsgContent.trim();
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