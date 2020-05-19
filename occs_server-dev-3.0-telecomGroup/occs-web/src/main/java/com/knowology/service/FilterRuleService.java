package com.knowology.service;

import com.knowology.dto.SpecialFilterCondition;
import com.knowology.model.DataStorage;
import com.knowology.model.FilterRule;

import java.util.List;

/**
 * 数据筛选(自动筛选) -- 规则
 * @author xullent
 */
public interface FilterRuleService {

    /**
     * 查出数据筛选规则(只存一条，多条默认第一条)
     * @return
     */
    FilterRule selectFilterRule();

    /**
     * 更新数据筛选规则
     * @param filterRule
     * @return
     */
    int updateFilterRule(FilterRule filterRule);

    /**
     * 号码组名称列表
     * @return
     */
    List<String> listTeleNumGroupName();

    /**
     * 加载省份
     * @param
     * @return
     */
    List<String> listProvinces();

    /**
     * 加载城市
     * @param
     * @return
     */
    List<String> listCitys();

    /**
     * 加载营业厅
     * @param
     * @return
     */
    List<String> listBusinessHallNames();

    /**
     * 加载业务类型
     * @param
     * @return
     */
    List<String> listBussinessTypes();

    /**
     * 按照条件筛选数据
     * @param specialFilterCondition
     * @return
     */
    List<DataStorage> loadDataFilter(SpecialFilterCondition specialFilterCondition);

    /**
     * 筛选大致数量
     * @param specialFilterCondition
     * @return
     */
    int countDataFilter(SpecialFilterCondition specialFilterCondition);
}
