package com.knowology.excelVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 营业厅服务满意率excel导出的样本接触状态sheet
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BussinessHallSample extends BaseRowModel {
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
    @ExcelProperty(value = "订单时间", index = 10)
    private Date compleTime;

    /**
     * 拨打时间
     */
    @ExcelProperty(value = "拨打时间", index = 11)
    private Date callTime;

    /**
     * Status
     */
    @ExcelProperty(value = "Status", index = 12)
    private String status;

    /**
     * Status-中文含义
     */
    @ExcelProperty(value = "Status-中文含义", index = 13)
    private String callStatus;
}
