package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 交互轮次统计
 * @author xullent
 */
@Data
public class Rounds {
    /**
     * 1
     */
    @Comment("1")
    private String rounds1;

    /**
     * 2
     */
    @Comment("2")
    private String rounds2;

    /**
     * 3
     */
    @Comment("3")
    private String rounds3;

    /**
     * 4
     */
    @Comment("4")
    private String rounds4;

    /**
     * 5
     */
    @Comment("5")
    private String rounds5;

    /**
     * 6
     */
    @Comment("6")
    private String rounds6;

    /**
     * 7
     */
    @Comment("7")
    private String rounds7;

    /**
     * >7
     */
    @Comment(">7")
    private String roundsGreater7;
}
