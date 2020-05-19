package com.knowology.config.annotation;

import java.lang.annotation.*;

/**
 * @author : Conan
 * @Description 成员姓名
 * @date : 2019-05-27 14:04
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FullName {
    String value() default "";
}
