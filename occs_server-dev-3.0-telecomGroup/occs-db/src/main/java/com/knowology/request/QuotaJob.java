package com.knowology.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>集团智能回访任务配额</p>
 *
 * @author : Conan
 * @date : 2019-12-06 11:20
 **/
@Data
public class QuotaJob {
    /**
     * 任务名
     */
    @NotBlank(message = "任务名不能为空")
    private String jobName;

    /**
     * 配额数据
     */
    private JSONArray quota;
}
