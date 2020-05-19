package com.knowology.annotation;

import java.lang.annotation.*;

/**
 * @author : Conan
 * @Description 标记方法非线程安全
 * @date : 2019-04-18 11:14
 **/
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NotThreadSafe {
    String value() default "";
}
