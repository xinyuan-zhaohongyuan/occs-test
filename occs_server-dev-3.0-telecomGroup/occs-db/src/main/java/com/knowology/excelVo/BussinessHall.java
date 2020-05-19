package com.knowology.excelVo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 营业厅服务满意率excel数据格式
 *
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BussinessHall extends BaseRowModel {

    /**
     * 记录序号
     */
    @ExcelProperty(value = "记录序号", index = 0)
    private String recordId;

    /**
     * 省份名称
     */
    @ExcelProperty(value = "省份", index = 1)
    private String province;

    /**
     * 地市
     */
    @ExcelProperty(value = "地市", index = 2)
    private String city;

    /**
     * 区号
     */
    @ExcelProperty(value = "区号", index = 3)
    private String areaCode;

    /**
     * 联系人号码
     */
    @ExcelProperty(value = "电话号码", index = 4)
    private String phoneNumber;

    /**
     * 服务类型
     */
    @ExcelProperty(value = "服务类型", index = 5)
    private String serviceType;

    /**
     * 产品类型
     */
    @ExcelProperty(value = "产品类型", index = 6)
    private String produceType;

    /**
     * 销售点名称
     */
    @ExcelProperty(value = "销售点名称", index = 7)
    private String salesName;

    /**
     * 销售点级别
     */
    @ExcelProperty(value = "销售点级别", index = 8)
    private String salesLevel;

    /**
     * 销售点类型
     */
    @ExcelProperty(value = "销售点类型", index = 9)
    private String salesType;

    /**
     * 订单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ExcelProperty(value = "订单时间", index = 10)
    private Date compleTime;

    /**
     * 来源标志
     */
    private Integer flag;

    /**
     * 创建时间
     */
    private Date createTime;
}
