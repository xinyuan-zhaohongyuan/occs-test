package com.knowology.dto;

import lombok.Data;

import java.util.List;

/**
 * <p>加载Jobdetail的条件，用于定时任务</p>
 *
 * @author : Conan
 * @date : 2019-08-07 18:26
 **/
@Data
public class JobDetailCondition {
    /**
     * 任务名
     */
    private String jobName;

    /**
     * 处理数据量
     */
    private Integer dealNum;

    /**
     * 重呼次数
     */
    private Integer callTimes;

    /**
     * 失败原因：可能多个
     */
    private List<String> list;

    /**
     * 重呼间隔
     */
    private Integer againCallInterval;

    /**
     * 省份
     */
    private String province;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 服务类型
     */
    private String serviceType;
    
    /**
     * 不用呼叫的省份
     */
    private List<String> noCallProvince;

	@Override
	public String toString() {
		return "JobDetailCondition [jobName=" + jobName + ", dealNum=" + dealNum + ", callTimes=" + callTimes
				+ ", list=" + list + ", againCallInterval=" + againCallInterval + ", province=" + province
				+ ", businessType=" + businessType + ", serviceType=" + serviceType + "]";
	}
    
    
}
