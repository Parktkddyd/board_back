package com.syp.board_back.configuration;

import com.syp.board_back.common.interceptor.SessionCheckInterceptor;
import com.syp.board_back.common.resolver.PageableValidArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SessionCheckInterceptor sessionCheckInterceptor;
    private final PageableValidArgumentResolver pageableValidArgumentResolver;

    public WebConfig(SessionCheckInterceptor sessionCheckInterceptor,
                     PageableValidArgumentResolver pageableValidArgumentResolver) {
        this.sessionCheckInterceptor = sessionCheckInterceptor;
        this.pageableValidArgumentResolver = pageableValidArgumentResolver;
    }

    @Value("${cors.allowed.origin}")
    private String allowedOrigin;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(pageableValidArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/login", "/users/signup", "/boards/**");
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
