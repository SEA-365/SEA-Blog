package com.sea.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 图片访问拦截配置：主要目的是映射到服务器上的图片位置
 * @author: sea
 * @date: 2024/1/10 11:15
 */
@Configuration
public class AccessImageInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/article/**")//前端url访问的路径，若有访问前缀，在访问时添加即可，这里不需添加。
//                .addResourceLocations("file:" + "/Volumes/BJFU/JavaProject/SEA-Blog/uploadFile/article/");//映射的服务器存放图片目录。【这里必须是绝对路径，否则前端访问不到！！！】
                .addResourceLocations("classpath:/uploadFile/article/");//映射的服务器存放图片目录。【这里必须是绝对路径，否则前端访问不到！！！】
    }

}
