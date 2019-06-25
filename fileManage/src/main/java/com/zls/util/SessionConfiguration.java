package com.zls.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfiguration implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    // 添加拦截器，使其生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * addInterceptor：添加自定义的拦截器
         * addPathPatterns("/**")：表示拦截所有请求
         * excludePathPatterns("/login.html","/regist.html") 登录与注册，直接放行
         */
        System.out.println("拦截器配置成功");
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns("/images/**","/js/**","/css/**","/login.html","/regist.html","/registReminder.html","/user/registUser","/user/checkLogin");

    }
}
