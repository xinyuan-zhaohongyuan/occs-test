package com.knowology.po;

import com.knowology.annotation.Comment;
import lombok.Data;

/**
 * 任务实时执行情况
 * @author xullent
 */
@Data
public class RealtimeExcute {
    /**
     * 总呼出
     */
    @Comment("总呼出")
    private String total;

    /**
     * 任务完成率
     */
    @Comment("任务完成率")
    private String completion;

    /**
     * 呼出成功率
     */
    @Comment("呼出成功率")
    private String success;

    /**
     * 今日呼出
     */
    @Comment("今日呼出")
    private String todayAmount;

    private String userHangup;

    public String getTotal() {
        return total;
    }

    public String getCompletion() {
        return completion;
    }

    public String getSuccess() {
        return success;
    }

    public String getTodayAmount() {
        return todayAmount;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setTodayAmount(String todayAmount) {
        this.todayAmount = todayAmount;
    }
}
