package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.Quota;
import org.apache.ibatis.annotations.Param;

public interface QuotaMapper extends MyMapper<Quota> {

    /**
     * 根据任务名称查询配额信息
     * @param jobName
     * @return
     */
    Quota selectQuotaByJobName(@Param("jobName") String jobName);
}