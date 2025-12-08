package com.compusfishqwq.compus_fishqwq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置访问 /uploads/** 对应本地 uploads 文件夹
        // 注意路径是相对于项目根目录
        registry.addResourceHandler("/uploads/**")
                 .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    }
}

