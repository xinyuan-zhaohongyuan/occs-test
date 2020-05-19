package com.knowology.request;

import org.hibernate.validator.constraints.Length;

import com.knowology.valid.AddCheck;
import com.knowology.valid.SearchCheck;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>外呼任务请求实体</p>
 *
 * @author : Conan
 * @date : 2019-08-01 16:10
 **/
@Data
@EqualsAndHashCode(callSuper=false)
public class JobQuery extends BaseQuery{
    /**
     * 任务id，对任务做修改的时候可能使用
     */
    private Integer id;

//    @Length(min = 1,max = 20,groups = {SearchCheck.class, AddCheck.class, DeleteCheck.class},message = "任务名称长度介于1-20字符")
    private String jobName;

    /**
     * 号码组名称
     */
    private String passivenumName;

    /**
     * 多个号码组名称
     */
    private String[] passivenumNameList;

    private String sceneName;

    private String playMode;

    @Length(max = 255,groups = {AddCheck.class},message = "描述长度不得超于255字符")
    private String description;

    @Length(max = 10,groups = {SearchCheck.class},message = "任务状态长度不得超于10字符")
    private String status;

    private String strategyName;

    private String timeStrategyName;

    private String creator;

    private String updateUser;

    //短信模板
    private String shortMsgModelName;

    //等待回复时间
    private Integer waitReplyTime;

    //自动外呼还是非自动，默认是0-非自动外呼
    private Integer isAuto = 0;
}
