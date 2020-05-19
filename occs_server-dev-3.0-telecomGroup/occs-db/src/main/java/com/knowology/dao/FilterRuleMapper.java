package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.FilterRule;

public interface FilterRuleMapper extends MyMapper<FilterRule> {

    /**
     * 查询出数据筛选规则
     * @return
     */
    FilterRule selectRule();
}