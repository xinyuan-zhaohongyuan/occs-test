package com.knowology.excelVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xullent
 * @date 2020/2/17 17:35
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PersonExcel extends BaseRowModel {

    @ExcelProperty(value = "JobId", index = 0)
    private String jobId;

    @ExcelProperty(value = "客户标志", index = 1)
    private String persion;

    @ExcelProperty(value = "对话内容", index = 2)
    private String text;

    @ExcelProperty(value = "url地址", index = 3)
    private String url;
}
