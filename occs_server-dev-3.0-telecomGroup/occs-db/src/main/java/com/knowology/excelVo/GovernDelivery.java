package com.knowology.excelVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 政企交付满意度/智慧家庭交付满意率Excel数据格式
 *
 *  @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GovernDelivery extends BaseRowModel {

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
     * 服务订单编码
     */
    @ExcelProperty(value = "服务订单编码", index = 3)
    private String salesNo;

    /**
     * 区号
     */
    @ExcelProperty(value = "区号", index = 4)
    private String areaCode;

    /**
     * 联系人号码
     */
    @ExcelProperty(value = "电话号码", index = 5)
    private String phoneNumber;

    /**
     * 服务类型
     */
    @ExcelProperty(value = "服务类型", index = 6)
    private String serviceType;

    /**
     * 产品大类
     */
    @ExcelProperty(value = "产品大类", index = 7)
    private String produceType;

    /**
     * 竣工时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ExcelProperty(value = "竣工时间", index = 8)
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
