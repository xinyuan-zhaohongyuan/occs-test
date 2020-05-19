package com.knowology.dto;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * JobDetail导出Excel时候需要的实体类
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class JobDetailExportExcel extends BaseRowModel {

    @ExcelProperty(value = "外呼ID", index = 0)
    private Long id;

    @ExcelProperty(value = "电话号码", index = 1)
    private String passiveNum;

    @ExcelProperty(value = "约访结果", index = 2)
    private String appointResult;

    @ExcelProperty(value = "外呼时间", index = 3)
    private Date callTime;

    @ExcelProperty(value = "满意度", index = 4)
    private Integer overall;

    @ExcelProperty(value = "呼叫状态", index = 5)
    private String callStatus;

    @ExcelProperty(value = "交互轮次", index = 6)
    private Integer rounds;

    @ExcelProperty(value = "通话时长", index = 7)
    private Integer talkTime;

    @ExcelProperty(value = "挂断方", index = 8)
    private String hangup;

    @ExcelProperty(value = "客户姓名", index = 9)
    private String clientName;

    @ExcelProperty(value = "营业厅", index = 10)
    private String businessHallName;

    @ExcelProperty(value = "短信发送时间", index = 11)
    private Date shortmsgSendTime;

    @ExcelProperty(value = "短信接收时间", index = 12)
    private Date shortmsgReceiveTime;
}
