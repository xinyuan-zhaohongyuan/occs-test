package com.knowology.request;

import lombok.Data;


/**
 * <p></p>
 *
 * @author : Conan
 * @date : 2019-07-22 18:49
 **/
@Data
public class BaseQuery {
    /**
     * pageNum默认值第一页
     */
//    @Pattern(regexp = "^[0-9]*$", message = "pageNum必须是数字")
    private Integer pageNum = 1 ;

    /**
     * pageSize默认值10
     */
//    @Pattern(regexp = "^[0-9]*$", message = "pageSize必须是数字")
    private Integer pageSize = 10;


}
