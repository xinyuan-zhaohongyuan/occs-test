package com.knowology.service;

import com.github.pagehelper.PageInfo;
import com.knowology.model.PassiveNum;
import com.knowology.model.PassiveNumDetail;

import java.util.List;

/**
* @ClassName PassiveNumService
* @Description (这里用一句话描述这个类的作用)
* @author Conan  :  没有真相
* @Date 2019/4/17 12:05
* @version 1.0.0
*/

public interface PassiveNumService {
    /**
     * 号码组列表数据
     * @param pageNum
     * @param pageSize
     * @param telenumGroupName
     * @return
     */
    PageInfo<PassiveNum> select(Integer pageNum, Integer pageSize, String telenumGroupName);

    /**
     * 通过ID查询号码组数据
     * @param id
     * @return
     */
    PassiveNum selectTeleNumGroupInfo(Integer id);

    /**
     * 号码组详情列表(通过号码组)
     * @param id
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<PassiveNumDetail> listDetail(Integer id, Integer pageSize, Integer pageNum,String passiveNum);


    /**
     * 插入号码组数据
     * @param passiveNum
     * @param passiveNumDetailsArrayList
     */
    void addPassiveNum(PassiveNum passiveNum, List<PassiveNumDetail> passiveNumDetailsArrayList);

    /**
     * 删除号码组详情数据
     * @param id
     * @return
     */
    int deleteDetail(Integer id);

    /**
     * 删除号码组数据
     * @param id
     */
    void deletePassiveNum(Integer id);

    /**
     * 编辑添加号码组数据
     * @param telenumGroupName
     * @param passiveNumDetailsArrayList
     */
    void addPassiveNumDetail(String telenumGroupName, List<PassiveNumDetail> passiveNumDetailsArrayList);

    /**
     * 更新号码组信息
     * @param passiveNum
     * @return
     */
    int updatePassiveNum(PassiveNum passiveNum);

    /**
     * 获取全部号码组数据
     * @return
     */
    List<PassiveNum> selectTeleNumGroupName();

    /**
     * 新增号码组
     * @param passiveNum
     * @return
     */
    int add(PassiveNum passiveNum);

    /**
     * 根据号码组ID查询号码组详细信息
     * @param id
     * @return
     */
    List<PassiveNumDetail> listPassiveNumDetail(Integer id);

    /**
     * 通过号码组名称查询是否存在号码组信息
     * @param telenumGroupName
     * @return
     */
    int selectPassiveNumByName(String telenumGroupName);

    /**
     * 拿到所有的号码组信息
     * @return
     */
    List<PassiveNum> selectAll();

    /**
     * 未同步的号码
     * @param telenumGroupName
     * @param dealNum
     * @return
     */
    List<PassiveNumDetail> selectNoSynByTelenumGroupName(String telenumGroupName,Integer dealNum);

    /**
     * 批量插入号码组
     * @param passiveNumDetailList
     * @return
     */
    int addPassivenumDetailByPassiveNumId(List<PassiveNumDetail> passiveNumDetailList);

    /**
     * 通过号码组名称查询
     * @param telenumName
     * @return
     */
    List<PassiveNumDetail> selectByTelenumName(String telenumName);

}
