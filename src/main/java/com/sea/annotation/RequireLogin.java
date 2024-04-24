package com.sea.annotation;

/**
 * 权限控制，要求用户已登录
 * @author: sea
 * @date: 2024/4/24 11:17
 */
import java.lang.annotation.*;

@Target(ElementType.METHOD) //指定注解的作用目标为方法。这意味着 @OperationLogSys 注解只能用于方法上。
@Retention(RetentionPolicy.RUNTIME) //指定注解的生命周期为运行时。这意味着这个注解会保留在运行时，因此可以通过反射获取到。
@Documented //这是一个标记注解，用于指示将该注解包含在生成的文档中。
public @interface RequireLogin {

}