package com.sea;

import com.sea.shiro.MySessionManager;
import com.sea.shiro.MyShiroRealm;
import com.sea.shiro.SpringSecurityBCryptMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 轻量级安全框架Shiro配置类
 * @author: sea
 * @date: 2023/11/15 09:49
 */
@Configuration
public class ShiroConfig {
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    private static final String TAG = "ShiroConfig ====> ";


    //引入application.yml文件中的Redis配置

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;


    // 在 Shiro 配置中配置 CredentialsMatcher
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        log.info(TAG + "hashedCredentialsMatcher()");
        //由于框架之间的算法实现不一致，在Shiro中仍然需要使用Spring Security的BCrypt实现
        SpringSecurityBCryptMatcher matcher = new SpringSecurityBCryptMatcher();
        matcher.setHashAlgorithmName("BCrypt");
        matcher.setHashIterations(10);
        return matcher;
    }


    /**
     * 配置自定义Realm：实现了自定义的身份认证和授权逻辑
     */
    @Bean
    public MyShiroRealm myShiroRealm(){
        log.info(TAG + "myShiroRealm()");
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
     * 配置安全管理器组件：提供了全局的安全策略和配置，确保在整个应用中保持一致的安全行为。
     */
    @Bean
    public SecurityManager securityManager(){
        log.info(TAG + "securityManager()");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());

        //增加redis配置

        //1.redis会话管理
        defaultWebSecurityManager.setSessionManager(sessionManager());
        //2.redis缓存管理
        defaultWebSecurityManager.setCacheManager(cacheManager());

        return defaultWebSecurityManager;
    }



    /**
     * 配置 Shiro 的过滤器链，定义了不同 URL 路径的访问规则
     * 例如：有些请求路径需要登录后才能访问，有些则可以直接访问；
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        log.info(TAG + "ShiroConfig.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //未登录时跳转
        shiroFilterFactoryBean.setLoginUrl("/users/unLogin");

        //无权限时跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/users/unAuth");

        //拦截器配置
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //"logout"是shiro内置的过滤器，负责处理用户登出逻辑
        filterChainDefinitionMap.put("/users/logout", "logout");

        // "anon"表示不需要认证即可访问，即配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/users/login", "anon");

        //"authc"表示需要认证（登录）才能访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info(TAG + "Shiro拦截器注入成功！");
        return shiroFilterFactoryBean;
    }

    /**
     * 启用 Shiro 的注解支持，允许在代码中使用 Shiro 的注解进行权限控制
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        log.info(TAG + "authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    /**
     *  配置Shiro安全框架的会话管理器
     */
    @Bean
    public SessionManager sessionManager(){
        log.info(TAG + "sessionManager()");
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * 配置Redis数据库连接
     */
    @Bean
    public RedisManager redisManager(){
        log.info(TAG + "redisManager()");
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);// 配置端口，但是好像没有这个方法[2.4.2.1-RELEASE版本的shiro-redis里面有这个方法]
        redisManager.setExpire(1800);// 配置缓存过期时间，但是好像没有这个方法[2.4.2.1-RELEASE旧版本的shiro-redis里面有这个方法]
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);

        return redisManager;
    }

    /**
     *  配置Redis缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager() {
        log.info(TAG + "cacheManager()");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     *  管理基于Redis的Shiro会话
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        log.info(TAG + "redisSessionDAO()");
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        // 设置使用Redis会话管理
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }




}
