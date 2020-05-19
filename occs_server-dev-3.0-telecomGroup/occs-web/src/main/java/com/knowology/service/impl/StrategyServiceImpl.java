package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.StrategyDetailMapper;
import com.knowology.model.StrategyDetail;
import com.knowology.service.StrategyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外呼策略
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class StrategyServiceImpl implements StrategyService {

    @Resource
    private StrategyDetailMapper strategyDetailMapper;

    @Override
    public PageInfo<StrategyDetail> selectStrategyList(Integer pageNum, Integer pageSize, String strategyName) {
        PageHelper.startPage(pageNum,pageSize);
        List<StrategyDetail> strategyDetails = strategyDetailMapper.selectStrategyList(strategyName);

        Iterator<StrategyDetail> iterator = strategyDetails.iterator();
        while (iterator.hasNext()) {
            StrategyDetail obj = (StrategyDetail)iterator.next();
            if("不重呼".equals(obj.getStrategyName())){
                iterator.remove();
            }
        }
        PageInfo<StrategyDetail> info = new PageInfo<>(strategyDetails);
        return info;
    }

    @Override
    public List<String> getStrategyList(){
        Example example = new Example(StrategyDetail.class);
        example.selectProperties("strategyName");
        List<StrategyDetail> strategyDetails = strategyDetailMapper.selectByExample(example);
        return strategyDetails.stream().map(StrategyDetail::getStrategyName).collect(Collectors.toList());
    }

    @Override
    public int deleteStrategy(Long id) {
        return strategyDetailMapper.deleteStrategy(id);
    }

    @Override
    public StrategyDetail selectStarttegy(Long id) {
        return strategyDetailMapper.selectStrategy(id);
    }

    @Override
    public int addStrategy(StrategyDetail strategyDetail) {
        return strategyDetailMapper.addStrategy(strategyDetail);
    }

    @Override
    public StrategyDetail selectOneByStrategyName(String strategyName) {
        Example example = new Example(StrategyDetail.class);
        example.createCriteria().andEqualTo("strategyName",strategyName);
        return strategyDetailMapper.selectOneByExample(example);
    }
}
