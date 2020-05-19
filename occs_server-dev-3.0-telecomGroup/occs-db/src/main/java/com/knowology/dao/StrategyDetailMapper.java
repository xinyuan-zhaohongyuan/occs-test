package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.StrategyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 黑名单
 * @author xullent
 */
public interface StrategyDetailMapper extends MyMapper<StrategyDetail> {
    /**
     * 查询外呼策略
     * @param strategyName
     * @return
     */
    List<StrategyDetail> selectStrategyList(@Param("strategyName") String strategyName);

    /**
     * 查看外呼策略
     * @param id
     * @return
     */
    StrategyDetail selectStrategy(@Param("id") Long id);
    /**
     * 删除外呼策略数据
     * @param id
     * @return
     */
    int deleteStrategy(Long id);

    /**
     * 新增外呼策略
     * @param strategyDetail
     * @return
     */
    int addStrategy(StrategyDetail strategyDetail);
}