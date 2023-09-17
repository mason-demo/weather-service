package com.mason.weather.core.annotation;

import com.mason.weather.core.config.ApiKeyInterceptorConfig;
import org.springframework.context.annotation.Import;

/**
 * Enables the API key interceptor for the application.
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@Import(ApiKeyInterceptorConfig.class)
public @interface EnableApiKeyInterceptor {
}
