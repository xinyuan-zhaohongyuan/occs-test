package com.knowology.excelVo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 营业厅服务满意率测评结果导出
 *
 * @author xullent
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BussinessHallResult extends BaseRowModel {
	/**
     * Z_BUSSINESS_DATA的id
     */
    private String id;
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
     * 答卷编号
     */
    @ExcelProperty(value = "答卷编号", index = 11)
    private String answerNum;

    /**
     * 访员登录名
     */
    @ExcelProperty(value = "访员登录名", index = 12)
    private String visitor;

    /**
     * 答卷开始时间
     */
    @ExcelProperty(value = "答卷开始时间", index = 13)
    private String answerBeginTime;

    /**
     * 答卷结束时间
     */
    @ExcelProperty(value = "答卷结束时间", index = 14)
    private String answerEndTime;

    /**
     * 通话时长
     */
    @ExcelProperty(value = "通话时长", index = 15)
    private Integer talkTime;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 16)
    private String gender;

    /**
     * 是否同意测评
     */
    @ExcelProperty(value = "是否同意测评", index = 17)
    private String agreeOrNot="0";

    /**
     * 最近是否有电信人员,给您所在的单位上门安装了组网专线
     */
    @ExcelProperty(value = "最近是否有电信人员,给您所在的单位上门安装了组网专线", index = 18)
    private String installReticle="0";

    /**
     * 您对这次安装上门服务满意吗
     */
    @ExcelProperty(value = "您对这次安装上门服务满意吗", index = 19)
    private String satisfied="0";

    /**
     * 您觉得这次上门服务哪方面需要改进?业务办理时间长
     */
    @ExcelProperty(value = "您觉得这次上门服务哪方面需要改进?业务办理时间长", index = 20)
    private String bussinesssLong="0";

    /**
     * 排队时间长
     */
    @ExcelProperty(value = "排队时间长", index = 21)
    private String waitingLong="0";

    /**
     * 秩序混乱
     */
    @ExcelProperty(value = "秩序混乱", index = 22)
    private String outOfOder="0";

    /**
     * 营业厅环境脏乱差
     */
    @ExcelProperty(value = "营业厅环境脏乱差", index = 23)
    private String environBad="0";

    /**
     * 服务态度差
     */
    @ExcelProperty(value = "服务态度差", index = 24)
    private String serviceAwful="0";

    /**
     * 业务能力差
     */
    @ExcelProperty(value = "业务能力差", index = 25)
    private String bussinessAwful="0";

    /**
     * 业务办理过程繁琐
     */
    @ExcelProperty(value = "业务办理过程繁琐", index = 26)
    private String bussinessFussy="0";

    /**
     * 设备故障
     */
    @ExcelProperty(value = "设备故障", index = 27)
    private String equipmentFailure="0";

    /**
     * 业务办理速度快
     */
    @ExcelProperty(value = "业务办理速度快", index = 28)
    private String speed="0";


    /**
     * 排队时间短
     */
    @ExcelProperty(value = "排队时间短", index = 29)
    private String shortQueuing="0";

    /**
     * 秩序良好
     */
    @ExcelProperty(value = "秩序良好", index = 30)
    private String eunomy="0";

    /**
     * 环境干净整洁
     */
    @ExcelProperty(value = "环境干净整洁", index = 31)
    private String cleanEnvironment="0";

    /**
     * 态度好
     */
    @ExcelProperty(value = "态度好", index = 32)
    private String  goodAttitude="0";

    /**
     * 业务熟练
     */
    @ExcelProperty(value = "业务熟练", index = 33)
    private String  businessSkilled="0";

    /**
     * 办理过程简单
     */
    @ExcelProperty(value = "办理过程简单", index = 34)
    private String  simpleProcess="0";

    /**
     * 顺利解决问题
     */
    @ExcelProperty(value = "顺利解决问题", index = 35)
    private String  smoothProblems="0";

    /**
     * 满意原因
     */
    private String satisReasion;

    /**
     * 满意原因详细
     */
    private String satisReasionTxt;

    /**
     * 不满意原因
     */
    private String noSatisReasion;

    /**
     * 不满意原因详细
     */
    private String noSatisReasionTxt;
    /**
     * 客户回答结果
     */
    private String intentfind;
    /**
     * 通话内容
     */
    private String content;
}
