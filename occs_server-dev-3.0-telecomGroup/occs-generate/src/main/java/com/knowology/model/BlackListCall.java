package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_BLACKLISTCALL")
public class BlackListCall {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 电话号码
     */
    @Column(name = "BLACK_NUM")
    private String blackNum;

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
     * 获取电话号码
     *
     * @return BLACK_NUM - 电话号码
     */
    public String getBlackNum() {
        return blackNum;
    }

    /**
     * 设置电话号码
     *
     * @param blackNum 电话号码
     */
    public void setBlackNum(String blackNum) {
        this.blackNum = blackNum == null ? null : blackNum.trim();
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
}