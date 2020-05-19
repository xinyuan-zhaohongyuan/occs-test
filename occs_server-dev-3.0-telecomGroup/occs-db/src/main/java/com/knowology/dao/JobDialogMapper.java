package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.JobDialog;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface JobDialogMapper extends MyMapper<JobDialog> {

    /**
     * 通过任务名称查找对话信息
     * @param jobName
     * @return
     */
    List<JobDialog> selectJobContentByJobName(@Param("jobName") String jobName);
}