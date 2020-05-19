package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_SCENE")
public class Scene {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场景名称
     */
    @Column(name = "SCENE")
    private String scene;

    /**
     * 商家
     */
    @Column(name = "SHOP")
    private String shop;

    /**
     * 渠道
     */
    @Column(name = "CHANNEL")
    private String channel;

    /**
     * 省份
     */
    @Column(name = "PROVINCE")
    private String province;

    /**
     * 省份编码
     */
    @Column(name = "PROVINCE_CODE")
    private String provinceCode;

    /**
     * 城市
     */
    @Column(name = "CITY")
    private String city;

    /**
     * 城市编码
     */
    @Column(name = "CITY_CODE")
    private String cityCode;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 获取主键ID
     *
     * @return ID - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取场景名称
     *
     * @return SCENE - 场景名称
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置场景名称
     *
     * @param scene 场景名称
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    /**
     * 获取商家
     *
     * @return SHOP - 商家
     */
    public String getShop() {
        return shop;
    }

    /**
     * 设置商家
     *
     * @param shop 商家
     */
    public void setShop(String shop) {
        this.shop = shop == null ? null : shop.trim();
    }

    /**
     * 获取渠道
     *
     * @return CHANNEL - 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置渠道
     *
     * @param channel 渠道
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
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
     * 获取省份编码
     *
     * @return PROVINCE_CODE - 省份编码
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * 设置省份编码
     *
     * @param provinceCode 省份编码
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
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
     * 获取城市编码
     *
     * @return CITY_CODE - 城市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市编码
     *
     * @param cityCode 城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}