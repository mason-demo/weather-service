package com.mason.weather.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Swagger documentation.
 *
 * @author: Mason
 * @date: 2023/9/15
 */
@Configuration
public class SwaggerConfig {

    /**
     * Customize the API's meta information for Swagger UI.
     *
     * @return OpenAPI object with set meta information.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather Service API")
                        .version("0.1")
                        .description("Access current weather data for any location on Earth!")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
