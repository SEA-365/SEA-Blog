package com.sea.annotation;

import com.sea.enums.OperationTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author: sea
 * @date: 2023/12/18 16:35
 */
@Target(ElementType.METHOD) //指定注解的作用目标为方法。这意味着 @OperationLogSys 注解只能用于方法上。
@Retention(RetentionPolicy.RUNTIME) //指定注解的生命周期为运行时。这意味着这个注解会保留在运行时，因此可以通过反射获取到。
@Documented //这是一个标记注解，用于指示将该注解包含在生成的文档中。
public @interface OperationLogSys {
    String description() default "";//日志描述

    OperationTypeEnum operationType() default OperationTypeEnum.SYSTEM;//日志操作类型
}
