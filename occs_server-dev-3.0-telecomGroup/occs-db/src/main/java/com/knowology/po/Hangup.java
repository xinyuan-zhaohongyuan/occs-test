package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 挂断方统计
 * @author xullent
 */
@Data
public class Hangup {
    /**
     * 系统挂断
     */
    @Comment("系统")
    private String systemHangup;

    /**
     * 用户挂断
     */
    @Comment("用户")
    private String userHangup;
}
