package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_FEEDBACK_REPORT")
public class Feedback {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务名
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 场景
     */
    @Column(name = "SCENE")
    private String scene;

    /**
     * 节点标签
     */
    @Column(name = "NODE")
    private String node;

    /**
     * 统计的任务数据的日期
     */
    @Column(name = "JOB_DATE")
    private Date jobDate;

    /**
     * 该条记录生成时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务名
     *
     * @return JOB_NAME - 任务名
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名
     *
     * @param jobName 任务名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * 获取场景
     *
     * @return SCENE - 场景
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置场景
     *
     * @param scene 场景
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
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
     * 获取统计的任务数据的日期
     *
     * @return JOB_DATE - 统计的任务数据的日期
     */
    public Date getJobDate() {
        return jobDate;
    }

    /**
     * 设置统计的任务数据的日期
     *
     * @param jobDate 统计的任务数据的日期
     */
    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    /**
     * 获取该条记录生成时间
     *
     * @return CREATE_TIME - 该条记录生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置该条记录生成时间
     *
     * @param createTime 该条记录生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}