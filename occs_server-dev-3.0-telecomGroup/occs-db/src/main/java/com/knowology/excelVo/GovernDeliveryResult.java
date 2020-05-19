package com.knowology.excelVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 政企交付满意度/智慧家庭交付满意率测评结果导出
 *
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GovernDeliveryResult extends BaseRowModel {

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
     * 答卷编号
     */
    @ExcelProperty(value = "答卷编号", index = 9)
    private String answerNum;

    /**
     * 访员登录名
     */
    @ExcelProperty(value = "访员登录名", index = 10)
    private String visitor;

    /**
     * 答卷开始时间
     */
    @ExcelProperty(value = "答卷开始时间", index = 11)
    private Date answerBeginTime;

    /**
     * 答卷结束时间
     */
    @ExcelProperty(value = "答卷结束时间", index = 12)
    private Date answerEndTime;

    /**
     * 通话时长
     */
    @ExcelProperty(value = "通话时长", index = 13)
    private Integer talkTime;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 14)
    private String gender;
    
}
