package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.PassiveNum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 号码组
 * @author xullent
 */
public interface PassiveNumMapper extends MyMapper<PassiveNum> {
    /**
     * 通过号码组名称查询
     * @param telenumGroupName
     * @return
     */
    List<PassiveNum> selectPassiveNumByTelenumGroupName(@Param("telenumGroupName") String telenumGroupName);

    /**
     * 通过Id修改号码组信息名称
     * @param teleNumGroup
     * @param id
     * @return
     */
    int updateTeleNumGroupById(@Param("teleNumGroup") String teleNumGroup, @Param("id") Long id);

    /**
     * 通过Id查询号码组信息
     * @param id
     * @return
     */
    PassiveNum selectTeleNumGroupInfoById(Integer id);

    /**
     * 添加号码组
     * @param passiveNum
     * @return
     */
    int addPassiveNum(PassiveNum passiveNum);

    /**
     * 删除号码组
     * @param id
     * @return
     */
    int deletePassiveNumById(Integer id);

    /**
     * 通过号码组Id修改号码组
     * @param passiveNum
     * @return
     */
    int updatePassiveNumById(PassiveNum passiveNum);

    /**
     * 查询号码组名称和号码组Id
     * @return
     */
    List<PassiveNum> selectTeleNumGroupName();

    /**
     * 根据号码组名称查询号码组是否存在
     * @param telenumGroupName
     * @return
     */
    int selectPassiveNumByName(String telenumGroupName);
}