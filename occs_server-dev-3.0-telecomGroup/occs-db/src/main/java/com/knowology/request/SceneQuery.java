package com.knowology.request;

import com.knowology.valid.AddCheck;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-08-08 15:52
 **/
@Data
@EqualsAndHashCode(callSuper=false)
public class SceneQuery extends BaseQuery {
    /**
     * 场景名称
     */
    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "场景名称长度介于1-20字符")
    private String scene;

    /**
     * 商家
     */
    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "商家长度介于1-20字符")
    private String shop;

    /**
     * 渠道
     */
    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "渠道长度介于1-20字符")
    private String channel;

    /**
     * 省份
     */
    @Length(min = 1,max = 15,groups = {AddCheck.class},message = "省份长度介于1-15字符,请联系管理员调整")
    private String province;

    /**
     * 省份编码
     */
    @Length(min = 1,max = 15,groups = {AddCheck.class},message = "省份编码长度介于1-20字符")
    private String provinceCode;

    /**
     * 城市
     */
    @Length(min = 1,max = 15,groups = {AddCheck.class},message = "城市长度介于1-15字符,请联系管理员调整")
    private String city;

    /**
     * 城市编码
     */
    @Length(min = 1,max = 15,groups = {AddCheck.class},message = "城市编码长度介于1-15字符")
    private String cityCode;
}
