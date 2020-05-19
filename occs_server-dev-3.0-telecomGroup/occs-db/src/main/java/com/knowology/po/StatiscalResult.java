package com.knowology.po;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author : Conan
 * @Description 反馈结果统计分析列表数据PO
 * @date : 2019-04-28 18:16
 **/
@Data
public class StatiscalResult {
    /**
     * 城市
     */
    @Column(name = "CITY")
    private String city;
    /**
     * 营业厅名称
     */
    @Column(name = "BUSINESS_HALL_NAME")
    private String businessHallName;
    /**
     * 排名
     */
    @Column(name = "RANK")
    private Long rank;
    /**
     * 满意率
     */
    @Column(name = "PERCENT")
    private String percent;

    /**
     * 城市
     */
    @Column(name = "PROVINCE")
    private String province;
}
