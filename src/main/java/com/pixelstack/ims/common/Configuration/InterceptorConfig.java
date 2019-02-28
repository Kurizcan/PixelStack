package com.pixelstack.ims.common.Configuration;

import com.pixelstack.ims.common.Auth.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 官方推荐方式配置拦截器
 */

// @Configuration，标明了该类是一个配置类并且会将该类作为一个SpringBean添加到IOC容器内
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
        // 设置拦截器的过滤路径规则为 /** 。
        // 即拦截所有请求，通过判断是否有 @LoginRequired 注解决定是否需要登录
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
