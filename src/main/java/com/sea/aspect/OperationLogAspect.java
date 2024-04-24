package com.sea.aspect;

import com.alibaba.fastjson.JSON;
import com.sea.annotation.OperationLogSys;
import com.sea.model.vo.OperationLogVO;
import com.sea.service.OperationLogService;
import com.sea.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 操作日志切面
 * @author: sea
 * @date: 2023/12/18 16:49
 */
@Component
@Aspect //定义该类为切面面，并定义该通知类受Spring容器管理
public class OperationLogAspect {

    //日志打印
    public static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

    public static final String TAG = "OperationLogAspect ====> ";
    @Resource
    private OperationLogService operationLogService; // 注入OperationLogService，用于记录日志

    /**
     * 定义切点：
     *      匹配被注解 @OperationLogSys 修饰的方法
     */
    @Pointcut("@annotation(com.sea.annotation.OperationLogSys)")
    public void operationLogPointCut(){

    }

    /**
     * 前置通知
     * @param joinPoint 可以获取被通知方法的信息，如方法名称、参数
     */
    @Before("operationLogPointCut()")
    public void before(JoinPoint joinPoint){
        // 获取目标类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取目标方法名
        String methodName = joinPoint.getSignature().getName();

        // 打印日志
        log.info(TAG + " 正在执行方法： " + className + "." + methodName + "()");
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "operationLogPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        // 获取当前线程的请求属性RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从请求属性中解析出 HttpServletRequest 对象，通常用于获取 HTTP 请求相关的信息，如请求头、请求参数等。
        HttpServletRequest httpServletRequest = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 从连接点（joinPoint）中获取方法签名（MethodSignature），可以通过方法签名获取方法名、参数等信息。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 从方法签名中获取方法
        Method method = signature.getMethod();
        // 获取 OperationLogSys 注解。用于提取方法上标注的 OperationLogSys 注解的信息。
        OperationLogSys annotation = method.getAnnotation(OperationLogSys.class);

        OperationLogVO operationLogVO = new OperationLogVO();

        if(annotation != null){
            // 操作类型
            String operationType = annotation.operationType().getValue();
            operationLogVO.setOperationType(operationType);

            // ip地址
            String ipAddr = IpUtil.getIpAddr(httpServletRequest);
            operationLogVO.setOperationIp(ipAddr);

            // ip来源
            String ipInfo = IpUtil.getIpInfo(ipAddr);
            operationLogVO.setOperationLocation(ipInfo);

            // 操作人
            assert httpServletRequest != null;
            String username = httpServletRequest.getRemoteUser();
            operationLogVO.setOperationName(username);

            //操作方法名称
            String className = joinPoint.getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            operationLogVO.setMethod(methodName);

            // 方法参数
            Object[] args = joinPoint.getArgs();
            operationLogVO.setArgs(Arrays.toString(args));

            //返回结果
            String returnResult = JSON.toJSONString(result);
            operationLogVO.setReturnValue(returnResult);

            operationLogService.addOperationLog(operationLogVO);
        }
    }

}
