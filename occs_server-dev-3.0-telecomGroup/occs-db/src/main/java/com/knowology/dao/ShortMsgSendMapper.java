package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.ShortMsgSend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短信任务
 * @author xullent
 */
public interface ShortMsgSendMapper extends MyMapper<ShortMsgSend> {
    /**
     * 查询所有短信信息
     * @return
     */
    List<ShortMsgSend> selectShortMsgSend();

    /**
     * 查看短信任务
     * @param id
     * @return
     */
    ShortMsgSend selectShortMsgSendById(@Param("id") Integer id);

    /**
     * 新增短信任务
     * @param shortMsgSend
     * @return
     */
    int insertShortMsgSend(ShortMsgSend shortMsgSend);
}