package com.knowology.constant;

import lombok.Data;

/**
 * <p>通话时长定义</p>
 *
 * @author : Conan
 * @date : 2019-08-13 10:54
 **/
@Data
public class TalkTimeConstant {
    /**
     * 0-5s
     */
    public static final String ZERO_TO_FIVE = "0-5";
    /**
     * 5-10S
     */
    public static final String FIVE_TO_TEN = "5-10";
    /**
     * 10-20S
     */
    public static final String TEN_TO_TWENTY = "10-20";
    /**
     * 20-30S
     */
    public static final String TWENTY_TO_THIRTY = "20-30";
    /**
     * 30-60S
     */
    public static final String THIRTY_TO_ONE_MIN = "30-60";
    /**
     * 60-120S
     */
    public static final String ONE_MIN_TO_TWO_MIN = "60-120";
    /**
     * >120S
     */
    public static final String GREATER_THAN_TWO_MIN = ">120";

}
