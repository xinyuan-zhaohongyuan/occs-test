package com.knowology.config.annotation;

import java.lang.annotation.*;

/**
 * @author : Conan
 * @Description token中获取user
 * @date : 2019-05-24 14:49
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
    String value() default "";

    String defaultValue() default "";
}
