package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.ShortMsgDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShortMsgDetailMapper extends MyMapper<ShortMsgDetail> {

    /**
     * 通过短信Id查询短信详情信息
     * @param id
     * @return
     */
    List<ShortMsgDetail> selectShortMsgDetailByShortMsgId(Long id);


    /**
     * 批量存储短信任务详情
     * @param list
     */
    void insertBatchShortMsgDetail(List<ShortMsgDetail> list);

    /**
     * 查询未下发的短信
     * @param dealStatus
     * @param dealNum
     * @return
     */
    List<ShortMsgDetail> selectShortMsgByDealStatus(@Param("dealStatus")String dealStatus,@Param("dealNum")Integer dealNum);
}