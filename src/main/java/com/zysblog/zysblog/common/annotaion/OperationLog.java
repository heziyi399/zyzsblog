package com.zysblog.zysblog.common.annotaion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注该该注解的方法需要记录操作日志
 *
 * @author 陌溪
 * @date 2020年3月23日09:35:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 业务名称
     */
    String value() default "";

    /**
     * 是否将当前日志记录到数据库中
     */
    boolean save() default true;
}