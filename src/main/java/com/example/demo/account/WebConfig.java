package com.example.demo.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RalonlineAuthInterceptor())
                .addPathPatterns("/ralonline", "/ralonline/**")
                .excludePathPatterns("/ralonline/login", "/ralonline/authenticate", "/ralonline/logout");
    }
}
