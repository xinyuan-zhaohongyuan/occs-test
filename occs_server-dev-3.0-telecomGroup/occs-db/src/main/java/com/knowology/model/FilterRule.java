package com.knowology.model;

import javax.persistence.*;

@Table(name = "Z_FILTER_RULE")
public class FilterRule {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据来源
     */
    @Column(name = "DATA_SOURCE")
    private String dataSource;

    /**
     * 省份
     */
    @Column(name = "PROVINCE")
    private String province;

    /**
     * 业务类型
     */
    @Column(name = "BUSSINESS_TYPE")
    private String bussinessType;

    /**
     * 筛选比例
     */
    @Column(name = "FILTER_RATIO")
    private Integer filterRatio;

    /**
     * 号码组名称
     */
    @Column(name = "TELENUM_GROUP_NAME")
    private String telenumGroupName;

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
     * 获取数据来源
     *
     * @return DATA_SOURCE - 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 设置数据来源
     *
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
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
     * 获取筛选比例
     *
     * @return FILTER_RATIO - 筛选比例
     */
    public Integer getFilterRatio() {
        return filterRatio;
    }

    /**
     * 设置筛选比例
     *
     * @param filterRatio 筛选比例
     */
    public void setFilterRatio(Integer filterRatio) {
        this.filterRatio = filterRatio;
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
}