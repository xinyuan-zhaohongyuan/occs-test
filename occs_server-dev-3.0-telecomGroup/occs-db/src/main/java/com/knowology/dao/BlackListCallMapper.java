package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.BlackListCall;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlackListCallMapper extends MyMapper<BlackListCall> {
    /**
     * 查询黑名单
     * @param slurNum
     * @return
     */
    List<BlackListCall> selectBlackListCallResult(@Param("slurNum")String  slurNum);

    /**
     * 删除黑名单
     * @param id
     * @return
     */
    int deleteBlackNum(Long id);

    /**
     * 增加黑名单
     * @param blackListCall
     * @return
     */
    int addblackNum(BlackListCall blackListCall);
}