package com.knowology.service.impl;

import com.knowology.dao.DataStorageMapper;
import com.knowology.dao.FilterRuleMapper;
import com.knowology.dao.PassiveNumMapper;
import com.knowology.dto.SpecialFilterCondition;
import com.knowology.model.DataStorage;
import com.knowology.model.FilterRule;
import com.knowology.model.PassiveNum;
import com.knowology.service.FilterRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据筛选(自动筛选) -- 规则
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class FilterRuleServiceImpl implements FilterRuleService {

    @Resource
    private FilterRuleMapper filterRuleMapper;
    @Resource
    private PassiveNumMapper passiveNumMapper;
    @Resource
    private DataStorageMapper dataStorageMapper;

    @Override
    public FilterRule selectFilterRule() {
        List<FilterRule> list = filterRuleMapper.selectAll();
        if(list == null || list.size() <= 0){
            return null;
        }
        return list.get(0);
    }

    @Override
    public int updateFilterRule(FilterRule filterRule) {
        Example example = new Example(FilterRule.class);
        return filterRuleMapper.updateByExampleSelective(filterRule,example);
    }

    @Override
    public List<String> listTeleNumGroupName() {
        List<PassiveNum> passiveNumList = passiveNumMapper.selectTeleNumGroupName();
        List<String> teleNumGroupNames = passiveNumList.stream().map(PassiveNum::getTelenumGroupName).collect(Collectors.toList());
        return teleNumGroupNames;
    }

    @Override
    public List<String> listProvinces() {
        return dataStorageMapper.listProvinces();
    }

    @Override
    public List<String> listCitys() {
        return dataStorageMapper.listCitys();
    }

    @Override
    public List<String> listBusinessHallNames() {
        return dataStorageMapper.listBusinessHallNames();
    }

    @Override
    public List<String> listBussinessTypes() {
        return dataStorageMapper.listBussinessTypes();
    }

    @Override
    public List<DataStorage> loadDataFilter(SpecialFilterCondition specialFilterCondition) {
        return dataStorageMapper.loadDataFilter(specialFilterCondition);
    }

    @Override
    public int countDataFilter(SpecialFilterCondition specialFilterCondition) {
        return dataStorageMapper.countDataFilter(specialFilterCondition);
    }
}
