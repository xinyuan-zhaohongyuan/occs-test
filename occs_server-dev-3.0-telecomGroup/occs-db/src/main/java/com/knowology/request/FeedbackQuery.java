package com.knowology.request;

import lombok.Data;


/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-08-16 10:49
 **/
@Data
public class FeedbackQuery {

    /**
     * 场景名
     */
    private String scene;

    /**
     * 节点
     */
    private String node;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;
}
