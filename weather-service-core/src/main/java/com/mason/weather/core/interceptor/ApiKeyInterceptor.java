package com.mason.weather.core.interceptor;

import com.mason.weather.core.exception.AppException;
import com.mason.weather.core.service.ApiKeyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to validate the API key in incoming requests.
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    /**
     * Api Key service instance
     */
    @Autowired
    private ApiKeyService apiKeyService;

    /**
     * Check API Key existence, validity, access limit
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apikey = request.getParameter("apikey");

        if (StringUtils.isBlank(apikey)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "API Key is mandatory");
        }

        if (!apiKeyService.isValidApiKey(apikey)) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }

        if (!apiKeyService.isWithinRateLimit(apikey)) {
            throw new AppException(HttpStatus.TOO_MANY_REQUESTS, "API Key Rate Limit Exceeded");
        }

        return true;
    }
}
