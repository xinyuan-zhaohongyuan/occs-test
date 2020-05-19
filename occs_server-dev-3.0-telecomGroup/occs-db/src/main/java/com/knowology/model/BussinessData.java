package com.knowology.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "Z_BUSSINESS_DATA")
public class BussinessData {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 记录序号
     */
    @Column(name = "RECORD_ID")
    private String recordId;

    /**
     * 省份名称
     */
    @Column(name = "PROVINCE")
    private String province;

    /**
     * 本地网名称
     */
    @Column(name = "CITY")
    private String city;

    /**
     * 区号
     */
    @Column(name = "AREA_CODE")
    private String areaCode;

    /**
     * 联系人号码
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 销售点名称
     */
    @Column(name = "SALES_NAME")
    private String salesName;

    /**
     * 销售点级别
     */
    @Column(name = "SALES_LEVEL")
    private String salesLevel;

    /**
     * 销售点类型
     */
    @Column(name = "SALES_TYPE")
    private String salesType;

    /**
     * 入库时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 编号
     */
    @Column(name = "SALES_NO")
    private String salesNo;

    /**
     * 服务类型
     */
    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    /**
     * 竣工时间
     */
    @Column(name = "COMPLE_TIME")
    private Date compleTime;

    /**
     * 产品类型
     */
    @Column(name = "PRODUCE_TYPE")
    private String produceType;

    /**
     * 预留标记
     */
    @Column(name = "FLAG")
    private Integer flag;

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
     * 获取记录序号
     *
     * @return RECORD_ID - 记录序号
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 设置记录序号
     *
     * @param recordId 记录序号
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    /**
     * 获取省份名称
     *
     * @return PROVINCE - 省份名称
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份名称
     *
     * @param province 省份名称
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取本地网名称
     *
     * @return CITY - 本地网名称
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置本地网名称
     *
     * @param city 本地网名称
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取区号
     *
     * @return AREA_CODE - 区号
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置区号
     *
     * @param areaCode 区号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * 获取联系人号码
     *
     * @return PHONE_NUMBER - 联系人号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置联系人号码
     *
     * @param phoneNumber 联系人号码
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * 获取销售点名称
     *
     * @return SALES_NAME - 销售点名称
     */
    public String getSalesName() {
        return salesName;
    }

    /**
     * 设置销售点名称
     *
     * @param salesName 销售点名称
     */
    public void setSalesName(String salesName) {
        this.salesName = salesName == null ? null : salesName.trim();
    }

    /**
     * 获取销售点级别
     *
     * @return SALES_LEVEL - 销售点级别
     */
    public String getSalesLevel() {
        return salesLevel;
    }

    /**
     * 设置销售点级别
     *
     * @param salesLevel 销售点级别
     */
    public void setSalesLevel(String salesLevel) {
        this.salesLevel = salesLevel == null ? null : salesLevel.trim();
    }

    /**
     * 获取销售点类型
     *
     * @return SALES_TYPE - 销售点类型
     */
    public String getSalesType() {
        return salesType;
    }

    /**
     * 设置销售点类型
     *
     * @param salesType 销售点类型
     */
    public void setSalesType(String salesType) {
        this.salesType = salesType == null ? null : salesType.trim();
    }

    /**
     * 获取入库时间
     *
     * @return CREATE_TIME - 入库时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置入库时间
     *
     * @param createTime 入库时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取编号
     *
     * @return SALES_NO - 编号
     */
    public String getSalesNo() {
        return salesNo;
    }

    /**
     * 设置编号
     *
     * @param salesNo 编号
     */
    public void setSalesNo(String salesNo) {
        this.salesNo = salesNo == null ? null : salesNo.trim();
    }

    /**
     * 获取服务类型
     *
     * @return SERVICE_TYPE - 服务类型
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * 设置服务类型
     *
     * @param serviceType 服务类型
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    /**
     * 获取竣工时间
     *
     * @return COMPLE_TIME - 竣工时间
     */
    public Date getCompleTime() {
        return compleTime;
    }

    /**
     * 设置竣工时间
     *
     * @param compleTime 竣工时间
     */
    public void setCompleTime(Date compleTime) {
        this.compleTime = compleTime;
    }

    /**
     * 获取产品类型
     *
     * @return PRODUCE_TYPE - 产品类型
     */
    public String getProduceType() {
        return produceType;
    }

    /**
     * 设置产品类型
     *
     * @param produceType 产品类型
     */
    public void setProduceType(String produceType) {
        this.produceType = produceType == null ? null : produceType.trim();
    }

    /**
     * 获取预留标记
     *
     * @return FLAG - 预留标记
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置预留标记
     *
     * @param flag 预留标记
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}