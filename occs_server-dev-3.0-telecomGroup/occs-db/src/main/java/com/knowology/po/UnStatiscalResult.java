package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 不满意数据
 */
@Data
public class UnStatiscalResult {

    /**
     * 等候时长不满意
     */
    @Comment("等候时长")
    private String waitTime;
    /**
     * 服务态度不满意
     */
    @Comment("服务态度")
    private String service;
    /**
     * 业务技能不满意
     */
    @Comment("业务技能")
    private String skill;
    /**
     * 服务环境不满意
     */
    @Comment("服务环境")
    private String hallEnv;
    /**
     * 其他不满意
     */
    @Comment("其他")
    private String UnStatiscal;

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setHallEnv(String hallEnv) {
        this.hallEnv = hallEnv;
    }

    public void setUnStatiscal(String unStatiscal) {
        UnStatiscal = unStatiscal;
    }

    public String getWaitTime() {

        return waitTime;
    }

    public String getService() {
        return service;
    }

    public String getSkill() {
        return skill;
    }

    public String getHallEnv() {
        return hallEnv;
    }

    public String getUnStatiscal() {
        return UnStatiscal;
    }
}
