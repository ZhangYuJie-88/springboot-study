package com.example.springbootredis.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h3>springboot-study</h3>
 * <p></p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 17:31
 **/
@Configuration
@Slf4j
@RequiredArgsConstructor
public class MyWebAppConfig implements WebMvcConfigurer {
    private final IpUrlLimitInterceptor ipUrlLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipUrlLimitInterceptor).addPathPatterns("/**");
    }
}
