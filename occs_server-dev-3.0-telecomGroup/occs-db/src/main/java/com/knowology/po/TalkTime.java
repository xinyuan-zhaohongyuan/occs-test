package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 通话时长统计
 * @author xullent
 */
@Data
public class TalkTime {
    /**
     * 0-5s
     */
    @Comment("0-5s")
    private String talkTime0To5;

    /**
     * 5-10s
     */
    @Comment("5-10s")
    private String talkTime5To10;

    /**
     * 10-20s
     */
    @Comment("10-20s")
    private String talkTime10To20;

    /**
     * 20-30s
     */
    @Comment("20-30s")
    private String talkTime20To30;

    /**
     * 30-60s
     */
    @Comment("30-60s")
    private String talkTime30To60;

    /**
     * 30-60s
     */
    @Comment("60-120s")
    private String talkTime60To120;

    /**
     * >120s
     */
    @Comment(">120s")
    private String talkTimeGreater120;
}
