package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 满意度统计
 */
@Data
public class OverAll {
    /**
     * 1分
     */
    @Comment("1")
    private String overAll1;
    /**
     * 2分
     */
    @Comment("2")
    private String overAll2;
    /**
     * 3分
     */
    @Comment("3")
    private String overAll3;
    /**
     * 4分
     */
    @Comment("4")
    private String overAll4;
    /**
     * 5分
     */
    @Comment("5")
    private String overAll5;
    /**
     * 6分
     */
    @Comment("6")
    private String overAll6;
    /**
     * 7分
     */
    @Comment("7")
    private String overAll7;
    /**
     * 8分
     */
    @Comment("8")
    private String overAll8;
    /**
     * 9分
     */
    @Comment("9")
    private String overAll9;
    /**
     * 10分
     */
    @Comment("10")
    private String overAll10;

    /**
     * 10分
     */
    @Comment("平均值")
    private String average;

    public String getOverAll1() {
        return overAll1;
    }

    public String getOverAll2() {
        return overAll2;
    }

    public String getOverAll3() {
        return overAll3;
    }

    public String getOverAll4() {
        return overAll4;
    }

    public String getOverAll5() {
        return overAll5;
    }

    public String getOverAll6() {
        return overAll6;
    }

    public String getOverAll7() {
        return overAll7;
    }

    public String getOverAll8() {
        return overAll8;
    }

    public String getOverAll9() {
        return overAll9;
    }

    public String getOverAll10() {
        return overAll10;
    }

    public void setOverAll1(String overAll1) {
        this.overAll1 = overAll1;
    }

    public void setOverAll2(String overAll2) {
        this.overAll2 = overAll2;
    }

    public void setOverAll3(String overAll3) {
        this.overAll3 = overAll3;
    }

    public void setOverAll4(String overAll4) {
        this.overAll4 = overAll4;
    }

    public void setOverAll5(String overAll5) {
        this.overAll5 = overAll5;
    }

    public void setOverAl16(String overAll6) {
        this.overAll6 = overAll6;
    }

    public void setOverAll7(String overAll7) {
        this.overAll7 = overAll7;
    }

    public void setOverAll8(String overAll8) {
        this.overAll8 = overAll8;
    }

    public void setOverAll9(String overAll9) {
        this.overAll9 = overAll9;
    }

    public void setOverAll10(String overAll10) {
        this.overAll10 = overAll10;
    }
}
