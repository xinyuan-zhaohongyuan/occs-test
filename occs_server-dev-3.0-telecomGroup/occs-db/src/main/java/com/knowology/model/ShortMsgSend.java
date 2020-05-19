package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_SHORTMSG_SEND")
public class ShortMsgSend {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 号码组名称
     */
    @Column(name = "TELENUM_NAME")
    private String telenumName;

    private String[] telenumNameList;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 短信内容
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 短信任务名称
     */
    @Column(name = "SHORTMSG_NAME")
    private String shortmsgName;

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
     * 获取号码组名称
     *
     * @return TELENUM_NAME - 号码组名称
     */
    public String getTelenumName() {
        return telenumName;
    }

    /**
     * 设置号码组名称
     *
     * @param telenumName 号码组名称
     */
    public void setTelenumName(String telenumName) {
        this.telenumName = telenumName == null ? null : telenumName.trim();
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
     * @return CONTENT - 短信内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置短信内容
     *
     * @param content 短信内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String[] getTelenumNameList() {
        return telenumNameList;
    }

    public void setTelenumNameList(String[] telenumNameList) {
        this.telenumNameList = telenumNameList;
    }
}