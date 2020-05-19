package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.JobDetail;
import com.knowology.po.Reason;
import com.knowology.po.StatiscalResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : Conan
 * @Description 反馈结果统计
 * @date : 2019-04-26 14:20
 **/

public interface StatisticalResultService {

    /**
     * 加载省份列表
     * @return
     */
    List<String> listProvinces();


    /**
     * 加载JobDetail列表
     * @param jobName
     * @return
     */
    List<JobDetail> listJobDetails(@Param("jobName") String jobName);

    /**
     * 挂断方统计
     * @param
     * @return
     */
    List<Map<String,String>> listOverAllResult(String province);

    /**
     * 满意度排行 NEW
     * @param province
     * @return
     */
    PageInfo<StatiscalResult> listOverAllRankResult(Integer pageNum, Integer pageSize, String province);

    /**
     * 不满意原因统计
     * @return
     */
    List<Map<String,String>> unStatiscalResult();

    /**
     * 节点具体不满意原因
     * @param node
     * @return
     */
    List<Reason> unStatiscalList(String node);
 }
