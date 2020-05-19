package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.StrategyDetail;

import java.util.List;


/**
 * 外呼策略
 * @author xullent
 */
public interface StrategyService {
    /**
     * 查询外呼策略
     * @param pageNum
     * @param pageSize
     * @param strategyName
     * @return
     */
    PageInfo<StrategyDetail> selectStrategyList(Integer pageNum, Integer pageSize, String strategyName);

    /**
     *查询所有重呼策略名称
     * @return
     */
    List<String> getStrategyList();

    /**
     * 删除外呼策略
     * @param id
     * @return
     */
    int deleteStrategy(Long id);

    /**
     * 查看外呼策略详情
     * @param id
     * @return
     */
    StrategyDetail selectStarttegy(Long id);

    /**
     * 新增外呼策略
     * @param strategyDetail
     * @return
     */
    int addStrategy(StrategyDetail strategyDetail);

    /**
     * 根据策略名查询
     * @param strategyName
     * @return
     */
    StrategyDetail selectOneByStrategyName(String strategyName);
}
