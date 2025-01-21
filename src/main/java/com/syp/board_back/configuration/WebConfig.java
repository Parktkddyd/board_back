package com.syp.board_back.configuration;

import com.syp.board_back.common.interceptor.SessionCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SessionCheckInterceptor sessionCheckInterceptor;

    public WebConfig(SessionCheckInterceptor sessionCheckInterceptor) {
        this.sessionCheckInterceptor = sessionCheckInterceptor;
    }

    @Value("${cors.allowed.origin}")
    private String allowedOrigin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/login", "/users/signup");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
