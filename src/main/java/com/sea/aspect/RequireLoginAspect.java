package com.sea.aspect;

import com.sea.entity.User;
import com.sea.exception.BizException;
import com.sea.model.vo.UserVO;
import com.sea.service.UserService;
import com.sea.util.BeanCopyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 要求用户登录切面
 * @author: sea
 * @date: 2024/4/24 11:18
 */
@Aspect
@Component
public class RequireLoginAspect {
    //日志打印
    public static final Logger log = LoggerFactory.getLogger(RequireLoginAspect.class);

    public static final String TAG = "RequireLoginAspect ====> ";

    @Autowired // 自动注入UserService对象
    UserService userService;
    /**
     * 定义切点：
     *      匹配被注解 @RequireLogin 修饰的方法
     */
    @Pointcut("@annotation(com.sea.annotation.RequireLogin)")
    public void requireLoginPointCut(){

    }

    @Before("requireLoginPointCut()")
    public void checkLoggedIn(JoinPoint joinPoint) {
        // 获取目标类名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取目标方法名
        String methodName = joinPoint.getSignature().getName();

        // 打印日志
        log.info(TAG + " 正在执行方法： " + className + "." + methodName + "()");

        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            throw new BizException("无法获取用户信息");
        }
        log.info(TAG + " subject: " + subject.toString());
        Session session = subject.getSession();
        if (session == null) {
            throw new BizException("用户会话已失效");
        }
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new BizException("用户未登录");
        }
        log.info(TAG + " username: " + username);
        User user = userService.getUserByUsername(username);
        log.info(TAG + " user: " + user);
        if(user == null || user.getLoginStatus() == 0){
            throw new BizException("用户不存在或未登录");
        }
    }
}
