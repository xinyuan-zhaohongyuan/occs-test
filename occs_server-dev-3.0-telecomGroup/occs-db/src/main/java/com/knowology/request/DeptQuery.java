package com.knowology.request;

import com.knowology.valid.AddCheck;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-25 15:24
 **/
@Data
public class DeptQuery {
    /**
     * 本ID
     */
    private Integer deptId;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    @Length(min = 1,max = 20,groups = {AddCheck.class},message = "部门名称长度介于1-20字符")
    private String deptName;

}
