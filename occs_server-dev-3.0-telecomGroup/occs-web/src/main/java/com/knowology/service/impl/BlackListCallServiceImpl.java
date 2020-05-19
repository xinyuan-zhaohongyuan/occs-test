package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.BlackListCallMapper;
import com.knowology.model.BlackListCall;
import com.knowology.service.BlackListCallService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 黑名单
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class BlackListCallServiceImpl implements BlackListCallService {

    @Resource
    private BlackListCallMapper blackListCallMapper;

    @Override
    public PageInfo<BlackListCall> selectBlackListCallResult(Integer pageNum, Integer pageSize, String slurNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<BlackListCall> blackListCalls = blackListCallMapper.selectBlackListCallResult(slurNum);
        PageInfo<BlackListCall> info = new PageInfo<>(blackListCalls);
        return info;
    }

    @Override
    public List<BlackListCall> selectBlackList(String blackNum) {
        return blackListCallMapper.selectBlackListCallResult(blackNum);
    }

    @Override
    public int deleteBlackNum(Long id) {
        return blackListCallMapper.deleteBlackNum(id);
    }

    @Override
    public int addblackNum(BlackListCall blackListCall) {
        return blackListCallMapper.addblackNum(blackListCall);
    }
}
