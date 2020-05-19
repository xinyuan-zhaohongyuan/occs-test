package com.knowology.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.TimeStrategyMapper;
import com.knowology.model.TimeStrategy;
import com.knowology.service.TimeStrategyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 时间策略
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class TimeStrategyServiceImpl implements TimeStrategyService {
    @Resource
    private TimeStrategyMapper timeStrategyMapper;

    @Override
    public PageInfo<TimeStrategy> selectStrategyList(Integer pageNum, Integer pageSize, String strategyName) {
        PageHelper.startPage(pageNum,pageSize);
        List<TimeStrategy> timeStrategys = timeStrategyMapper.selectStrategyList(strategyName);
        for(TimeStrategy timeStrategy : timeStrategys){
            timeStrategy.setTimeQuantum( getTimeQuantum(timeStrategy.getExecuteTime()));
        }
        PageInfo<TimeStrategy> info = new PageInfo<>(timeStrategys);
        return info;
    }

    @Override
    public List<String> getStrategyList() {
        Example example = new Example(TimeStrategy.class);
        example.selectProperties("strategyName");
        List<TimeStrategy> timeStrategies = timeStrategyMapper.selectByExample(example);
        return timeStrategies.stream().map(TimeStrategy::getStrategyName).collect(Collectors.toList());
    }

    @Override
    public int deleteStrategy(Long id) {
        return timeStrategyMapper.deleteStrategy(id);
    }

    @Override
    public int addStrategy(TimeStrategy timeStrategy) {
        return timeStrategyMapper.addStrategy(timeStrategy);
    }

    @Override
    public TimeStrategy selectOneByStrategyName(String strategyName) {
        return timeStrategyMapper.selectStrategyByName(strategyName);
    }

    /**
     * JSONArray格式的执行时间拼接转换一下
     * @param executeTime
     * @return
     */
    private String getTimeQuantum(String executeTime){
        StringBuilder hourNum = new StringBuilder();

        JSONArray jsonArray = JSONArray.parseArray(executeTime);
        for(int i=0;i<jsonArray.size();i++){
            hourNum.append(jsonArray.getJSONObject(i).getString("beginTime") + "-" + jsonArray.getJSONObject(i).getString("endTime") + ",");
        }
        if(hourNum.toString().contains(",")){
            return hourNum.substring(0,hourNum.length()-1);
        }
        return hourNum.toString();
    }
}
