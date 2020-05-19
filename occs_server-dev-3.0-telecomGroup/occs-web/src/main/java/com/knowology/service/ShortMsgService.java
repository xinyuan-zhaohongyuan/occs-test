package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.ShortMsgModel;
import com.knowology.model.ShortMsgDetail;
import com.knowology.model.ShortMsgSend;

import java.util.List;

/**
 * 短信模板Service
 * @author xullent
 */
public interface ShortMsgService {

    /**
     * 短信模板列表数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ShortMsgModel> list(Integer pageNum, Integer pageSize);

    /**
     * 删除短信模板
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 新增短信模板
     * @param shortMsgModel
     * @return
     */
    int addShortMsg(ShortMsgModel shortMsgModel);

    /**
     * 获取短信模板名称列表
     * @return
     */
    List<String> shortMsgNameList();

    /**
     * 新建短信任务
     * @param entity
     * @param memberName
     * @return Long
     */
    int insertShortMsg(ShortMsgSend entity, String memberName);

    /**
     * 批量插入短信任务详情
     * @param list
     */
    void insertBatchShortMsgDetail(List<ShortMsgDetail> list);

    /**
     * 分页查询短信任务
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ShortMsgSend> select(Integer pageNum, Integer pageSize);

    /**
     * 分页，短信详情
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    PageInfo<ShortMsgDetail> selectDetail(Integer pageNum, Integer pageSize, Long id);

    /**
     * 通过ID获取短信任务数据
     * @param id
     * @return
     */
    ShortMsgSend selectShortMsgSendById(Integer id);

    /**
     * 通过id查询短信详情
     * @param id
     * @return
     */
    List<ShortMsgDetail> selectShortMsgDetailById(Long id);

    /**
     * 根据下发处理状态查询
     * @return
     */
    List<ShortMsgDetail> selectShortMsgByDealStatus(String dealStatus,Integer dealNum);

    void updateShortMsgDetail(ShortMsgDetail shortMsgDetail);

}
