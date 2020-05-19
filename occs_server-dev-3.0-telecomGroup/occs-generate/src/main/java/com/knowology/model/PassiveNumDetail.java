package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_PASSIVENUM_DETAILS")
public class PassiveNumDetail {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 电话号码
     */
    @Column(name = "PHONE_NUM")
    private String phoneNum;

    /**
     * 客户姓名
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     * 办理完成时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 年龄
     */
    @Column(name = "AGE")
    private String age;

    /**
     * 性别
     */
    @Column(name = "GENDER")
    private String gender;

    /**
     * 省份
     */
    @Column(name = "PROVINCE")
    private String province;

    /**
     * 城市
     */
    @Column(name = "CITY")
    private String city;

    /**
     * 营业厅
     */
    @Column(name = "BUSINESS_HALL_NAME")
    private String businessHallName;

    /**
     * 业务类型
     */
    @Column(name = "BUSSINESS_TYPE")
    private String bussinessType;

    /**
     * 号码组ID
     */
    @Column(name = "PASSIVENUM_ID")
    private Integer passivenumId;

    /**
     * 号码组名称
     */
    @Column(name = "TELENUM_GROUP_NAME")
    private String telenumGroupName;

    /**
     * 是否同步 1-同步 0-未同步
     */
    @Column(name = "IS_SYNCHRON")
    private Integer isSynchron;

    /**
     * 数据来源，默认0-手动添加 2-自动筛选 3-专项筛选
     */
    @Column(name = "ORIGIN")
    private Integer origin;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取电话号码
     *
     * @return PHONE_NUM - 电话号码
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置电话号码
     *
     * @param phoneNum 电话号码
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 获取客户姓名
     *
     * @return CLIENT_NAME - 客户姓名
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置客户姓名
     *
     * @param clientName 客户姓名
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * 获取办理完成时间
     *
     * @return CREATE_TIME - 办理完成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置办理完成时间
     *
     * @param createTime 办理完成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取年龄
     *
     * @return AGE - 年龄
     */
    public String getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    /**
     * 获取性别
     *
     * @return GENDER - 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 获取省份
     *
     * @return PROVINCE - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取城市
     *
     * @return CITY - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取营业厅
     *
     * @return BUSINESS_HALL_NAME - 营业厅
     */
    public String getBusinessHallName() {
        return businessHallName;
    }

    /**
     * 设置营业厅
     *
     * @param businessHallName 营业厅
     */
    public void setBusinessHallName(String businessHallName) {
        this.businessHallName = businessHallName == null ? null : businessHallName.trim();
    }

    /**
     * 获取业务类型
     *
     * @return BUSSINESS_TYPE - 业务类型
     */
    public String getBussinessType() {
        return bussinessType;
    }

    /**
     * 设置业务类型
     *
     * @param bussinessType 业务类型
     */
    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType == null ? null : bussinessType.trim();
    }

    /**
     * 获取号码组ID
     *
     * @return PASSIVENUM_ID - 号码组ID
     */
    public Integer getPassivenumId() {
        return passivenumId;
    }

    /**
     * 设置号码组ID
     *
     * @param passivenumId 号码组ID
     */
    public void setPassivenumId(Integer passivenumId) {
        this.passivenumId = passivenumId;
    }

    /**
     * 获取号码组名称
     *
     * @return TELENUM_GROUP_NAME - 号码组名称
     */
    public String getTelenumGroupName() {
        return telenumGroupName;
    }

    /**
     * 设置号码组名称
     *
     * @param telenumGroupName 号码组名称
     */
    public void setTelenumGroupName(String telenumGroupName) {
        this.telenumGroupName = telenumGroupName == null ? null : telenumGroupName.trim();
    }

    /**
     * 获取是否同步 1-同步 0-未同步
     *
     * @return IS_SYNCHRON - 是否同步 1-同步 0-未同步
     */
    public Integer getIsSynchron() {
        return isSynchron;
    }

    /**
     * 设置是否同步 1-同步 0-未同步
     *
     * @param isSynchron 是否同步 1-同步 0-未同步
     */
    public void setIsSynchron(Integer isSynchron) {
        this.isSynchron = isSynchron;
    }

    /**
     * 获取数据来源，默认0-手动添加 2-自动筛选 3-专项筛选
     *
     * @return ORIGIN - 数据来源，默认0-手动添加 2-自动筛选 3-专项筛选
     */
    public Integer getOrigin() {
        return origin;
    }

    /**
     * 设置数据来源，默认0-手动添加 2-自动筛选 3-专项筛选
     *
     * @param origin 数据来源，默认0-手动添加 2-自动筛选 3-专项筛选
     */
    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
}