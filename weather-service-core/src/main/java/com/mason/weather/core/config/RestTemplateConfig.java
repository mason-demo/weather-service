package com.mason.weather.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate bean.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Provides a new RestTemplate instance.
     *
     * @return new RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
