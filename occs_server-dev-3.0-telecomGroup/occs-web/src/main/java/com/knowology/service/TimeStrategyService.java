package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.TimeStrategy;

import java.util.List;

/**
 * 时间策略
 * @author xullent
 */
public interface TimeStrategyService {
    /**
     * 查询外呼策略
     * @param pageSize
     * @param pageNum
     * @param strategyName
     * @return
     */
    PageInfo<TimeStrategy> selectStrategyList(Integer pageNum, Integer pageSize, String strategyName);

    /**
     * 获取外呼策略名称
     * @return
     */
    List<String> getStrategyList();

    /**
    * 删除外呼策略
    *  @param id
    *  @return
    */
    int deleteStrategy(Long id);

    /**
     * 新增外呼策略
     * @param timeStrategy
     * @return
     */
    int addStrategy(TimeStrategy timeStrategy);

    /**
     * 根据时间策略名称，查出对应的时间策略
     * @return
     */
    TimeStrategy selectOneByStrategyName(String strategyName);
}
