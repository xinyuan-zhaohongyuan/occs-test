package com.knowology.po;


import lombok.Data;

/**
 * 短信任务类
 * @author xullent
 */
@Data
public class ShortMsgJobDetail {

    /**
     * 任务ID
     */
    private Integer id;

    /**
     * 任务ID
     */
    private String jobName;

    /**
     * 电话号码
     */
    private String passiveNum;

    /**
     * 短信模板内容
     */
    private String shortMsgContent;

    public Integer getId() {
        return id;
    }

    public String getPassiveNum() {
        return passiveNum;
    }

    public String getShortMsgContent() {
        return shortMsgContent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassiveNum(String passiveNum) {
        this.passiveNum = passiveNum;
    }

    public void setShortMsgContent(String shortMsgContent) {
        this.shortMsgContent = shortMsgContent;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
