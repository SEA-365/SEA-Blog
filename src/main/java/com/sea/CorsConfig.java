package com.sea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 解决全局跨域问题
 * @author: sea
 * @date: 2023/11/15 17:30
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //日志打印
    private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    private static final String TAG = "ResourcesConfig ====> ";


    /**
     * 配置全局跨域
     */
    private CorsConfiguration buildConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));

        //header，允许哪些header
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);

        //允许的请求方法，POST、GET等
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);

        corsConfiguration.setAllowCredentials(true);

        log.info(TAG + corsConfiguration.checkOrigin("http://localhost:7070"));

        return corsConfiguration;
    }

    /**
     * 配置 CORS 过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());//跨域全局配置作用于所有请求路径（"/**"）

        //设置跨域过滤器优先级
//        FilterRegistrationBean<CorsFilter> cors = new FilterRegistrationBean<>(new CorsFilter(source));
//        cors.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return new CorsFilter(source);
    }
}
