package com.mason.weather;

import com.mason.weather.core.annotation.EnableApiKeyInterceptor;
import com.mason.weather.core.mocking.MockApiKeys;
import com.mason.weather.core.model.domain.ApiKey;
import com.mason.weather.core.service.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Weather Geocoding Service application.
 *
 * @author: Mason
 * @date: 2023/9/15
 */
@SpringBootApplication
@EnableApiKeyInterceptor
public class WeatherServiceApiApplication implements CommandLineRunner {

    /**
     * API Key Service, it is used here to generate mock API Keys.
     */
    @Autowired
    private ApiKeyService apiKeyService;

    /**
     * Starts the Weather Geocoding Service application.
     */
    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApiApplication.class, args);
    }

    /**
     * Run when the application is fully started.
     */
    @Override
    public void run(String... args) throws Exception {
        insertDefaultApiKeys();
    }

    /**
     * Mock API keys from the MockApiKeys enumeration.
     */
    private void insertDefaultApiKeys() {
        for (MockApiKeys key : MockApiKeys.values()) {
            ApiKey apiKey = new ApiKey();
            apiKey.setApikey(key.value());
            apiKeyService.save(apiKey);
        }
    }
}
