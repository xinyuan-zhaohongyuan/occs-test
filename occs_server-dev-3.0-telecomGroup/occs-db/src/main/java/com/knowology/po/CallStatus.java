package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 呼叫状态
 * @author xullent
 */
@Data
public class CallStatus {


    /**
     * 成功
     */
    @Comment("成功")
    private String callStatusSuccess;

    /**
     * 拒接
     */
    @Comment("拒接")
    private String callStatusReject;

    /**
     * 未接
     */
    @Comment("未接")
    private String callStatusFailre;

    /**
     * 其他
     */
    @Comment("其他")
    private String callStatusOther;
}
