package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.BlackListCall;

import java.util.List;

/**
 * 黑名单
 * @author xullent
 */
public interface BlackListCallService {

    /**
     * 查询黑名单
     * @param pageNum
     * @param pageSize
     * @param slurNum
     * @return
     */
    PageInfo<BlackListCall> selectBlackListCallResult(Integer pageNum, Integer pageSize, String slurNum);

    /**
     * 根据号码查询
     * @param blackNum
     * @return
     */
    List<BlackListCall> selectBlackList(String blackNum);

    /**
     * 删除黑名单
     * @param id
     * @return
     */
    int deleteBlackNum(Long id);

    /**
     * 新增黑名单
     * @param blackListCall
     * @return
     */
    int addblackNum(BlackListCall blackListCall);
}
