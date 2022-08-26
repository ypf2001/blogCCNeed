package com.ypf.ccneed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-27 18:17
 **/
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    /*解决跨域问题
     * @Param: [registry]
     * @Return: void
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
