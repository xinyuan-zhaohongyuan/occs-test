package com.knowology.request;

import com.knowology.valid.AddCheck;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-24 16:57
 **/
@Data
public class AuthorityQuery {

    private Integer roleId;

    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "角色名称长度介于1-20字符")
    private String roleName;

    private List<Integer> menuIds;
}
