package com.knowology.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Z_EXAMPLE")
public class ExampleData {
    @Id
    @Column(name = "ID")
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    /**
     * 被叫号码
     */
    @Column(name = "PASSIVE_NUM")
    private String passiveNum;

    /**
     * 外呼发起时间
     */
    @Column(name = "START_TIME")
    private Date startTime;

    /**
     * 场景选择
     */
    @Column(name = "SCENE")
    private String scene;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

    /**
     * 放音类型
     */
    @Column(name = "MODE")
    private String mode;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

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
     * 获取外呼发起时间
     *
     * @return START_TIME - 外呼发起时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置外呼发起时间
     *
     * @param startTime 外呼发起时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取场景选择
     *
     * @return SCENE - 场景选择
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置场景选择
     *
     * @param scene 场景选择
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
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
     * 获取放音类型
     *
     * @return MODE - 放音类型
     */
    public String getMode() {
        return mode;
    }

    /**
     * 设置放音类型
     *
     * @param mode 放音类型
     */
    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
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
}