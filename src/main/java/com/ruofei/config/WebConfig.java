package com.ruofei.config;

import com.ruofei.Interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: srf
 * @Date: 2020/12/1 14:50
 * @description:注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
            .addPathPatterns("/**").excludePathPatterns("/**/login");
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

}
