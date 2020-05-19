package com.knowology.request;

import com.knowology.valid.AddCheck;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>用户管理</p>
 *
 * @author : Conan
 * @date : 2019-07-22 18:42
 **/
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfoQuery extends BaseQuery {

    /**
     * 用户ID，修改用户的时候使用
     */
    private Integer userId;
    /**
     * 用户姓名
     */
    @Length(min = 1,max = 10,groups = {AddCheck.class},message = "姓名长度介于1-10字符")
    private String fullName;

    /**
     * 用户名
     */
    @NotBlank(groups = {AddCheck.class},message = "用户名不能为空")
    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "用户名长度介于1-20字符")
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 所属团队ID
     */
    private Integer deptId;

    /**
     * 角色ID,可以是多个
     */
    private List<Integer> roleIds;

}
