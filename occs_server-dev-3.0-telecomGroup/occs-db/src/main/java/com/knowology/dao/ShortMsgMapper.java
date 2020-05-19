package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.ShortMsgModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短信模板
 * @author xullent
 */
public interface ShortMsgMapper extends MyMapper<ShortMsgModel> {

    /**
     * 根据ID删除短信模板
     * @param id
     * @return
     */
    int deleteShortMsg(@Param("id") Long id);

    /**
     * 根据ID查询短信模板
     * @param id
     * @return
     */
    ShortMsgModel selectByName(@Param("shortmsgName") String shortmsgName);

    /**
     * 查询短信模板
     * @return
     */
    List<ShortMsgModel> selectShortMsgModelList();
}