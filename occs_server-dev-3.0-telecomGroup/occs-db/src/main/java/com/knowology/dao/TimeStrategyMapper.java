package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.TimeStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 时间策略
 * @author xullent
 */
public interface TimeStrategyMapper extends MyMapper<TimeStrategy> {
    /**
     * 查询时间策略
     * @param
     * @return
     */
    List<TimeStrategy> selectStrategyList(@Param("strategyName") String strategyName);

    /**
     * 删除时间策略数据
     * @param id
     * @return
     */
    int deleteStrategy(Long id);

    /**
     * 新增时间策略
     * @param timeStrategy
     * @return
     */
    int addStrategy(TimeStrategy timeStrategy);

    /**
     * 通过Name查询时间策略名称
     * @param strategyName
     * @return
     */
    TimeStrategy selectStrategyByName(@Param("strategyName") String strategyName);
}