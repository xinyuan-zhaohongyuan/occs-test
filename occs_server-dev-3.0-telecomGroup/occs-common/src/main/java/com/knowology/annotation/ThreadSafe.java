package com.knowology.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Conan
 * @Description 标记方法线程安全
 * @date : 2019-04-18 11:19
 **/
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface ThreadSafe {
    String value() default "";
}
