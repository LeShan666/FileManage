package com.zls.util;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.text.DecimalFormat;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //factory.setMaxFileSize(1024);
        //单个文件最大
        factory.setMaxFileSize("51200KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }


}
