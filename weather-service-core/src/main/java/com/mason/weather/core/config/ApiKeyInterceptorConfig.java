package com.mason.weather.core.config;

import com.mason.weather.core.interceptor.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up the ApiKeyInterceptor.
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@Configuration
public class ApiKeyInterceptorConfig implements WebMvcConfigurer {

    /**
     * An instance of the ApiKeyInterceptor.
     */
    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    /**
     * Configures the interceptor for the application.
     * Bypass swagger related path
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**");
    }

}
