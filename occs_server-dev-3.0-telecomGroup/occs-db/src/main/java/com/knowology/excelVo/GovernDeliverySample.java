package com.knowology.excelVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 政企交付满意度/智慧家庭交付满意率excel导出的样本接触状态sheet
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GovernDeliverySample extends BaseRowModel {
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
    @ExcelProperty(value = "竣工时间", index = 8)
    private Date compleTime;

    /**
     * 拨打时间
     */
    @ExcelProperty(value = "拨打时间", index = 9)
    private Date callTime;

    /**
     * Status
     */
    @ExcelProperty(value = "Status", index = 10)
    private Integer status;

    /**
     * Status-中文含义
     */
    @ExcelProperty(value = "Status-中文含义", index = 11)
    private String callStatus;
}
