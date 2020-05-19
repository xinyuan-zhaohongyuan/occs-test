package com.knowology.dao;

import com.knowology.config.MyMapper;
import com.knowology.model.PassiveNumDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassiveNumDetailMapper extends MyMapper<PassiveNumDetail> {
    /**
     * 通过号码组Id查询号码组的详情信息
     * @param id
     * @return
     */
    List<PassiveNumDetail> selectTeleNumGroupDetailById(@Param("id")Integer id,@Param("phoneNum") String phoneNum);

    /**
     * 通过号码组Id添加号码详情信息(添加号码)
     * @param passiveNumDetailsArrayList
     * @return
     */
    int addPassivenumDetailByPassiveNumId(List<PassiveNumDetail> passiveNumDetailsArrayList);

    /**
     * 根据详情信息Id删除详情信息(电话号)
     * @param id
     * @return
     */
    int deleteDetailById(Integer id);

    /**
     * 根据详情信息Id添加详情信息(电话号)
     * @param passiveNumDetail
     * @return
     */
    int addDetailById(PassiveNumDetail passiveNumDetail);

    /**
     * 根据号码组Id删除详情信息(电话号)
     * @param id
     * @return
     */
    int deleteDetailByPassiveNumId(Integer id);

    /**
     * 通过号码名称查询
     * @param phoneNum
     * @return
     */
    PassiveNumDetail selectTeleNumGroupDetailByName(@Param("phoneNum") String phoneNum);

    /**
     * 根据号码组名称查被叫号码数量
     * @param telenumGroupName
     * @return
     */
    int countTeleNumGroupDetailByName(@Param("telenumGroupName") String telenumGroupName);

    /**
     * 通过号码组名称和号码查询号码详情
     * @param telenumGroupName
     * @param phoneNum
     * @return
     */
    PassiveNumDetail selectPassiveNumDetailByPassNum(@Param("telenumGroupName") String telenumGroupName,@Param("phoneNum") String phoneNum);

    /**
     *
     * @return
     */
    List<PassiveNumDetail> selectNoSynByTelenumGroupName(@Param("telenumGroupName")String telenumGroupName,@Param("dealNum")Integer dealNum);

    /**
     * 改变这一批的同步状态
     * @param ids
     */
    void incrementSynChronStatus(List<Integer> ids);

    /**
     * 根据号码组Id删除详情信息(电话号)
     * @param telenumGroupName
     * @return
     */
    int deleteDetailByPassiveNumName(String telenumGroupName);
}