package com.knowology.service;

import com.knowology.model.JobDialog;

import java.util.List;

/**
 * @author xullent
 * @date 2020/2/18 14:45
 */

public interface JobDialogService {

    /**
     * 呼叫内容文本
     * @param jobName
     * @return
     */
    List<JobDialog> selectJobContentByJobName(String jobName);
}