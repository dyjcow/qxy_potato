package com.qxy.potatos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：Dyj
 * @date ：Created in 2022/8/31 20:03
 * @description：初始化AI的注解
 * @modified By：
 * @version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitAIHand {
}
