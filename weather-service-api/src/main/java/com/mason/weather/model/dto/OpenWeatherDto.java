package com.mason.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for OpenWeather response
 *
 * @author: Mason
 * @date: 2023/9/17
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores unknown filed to avoid UnrecognizedPropertyException
public class OpenWeatherDto {

    /**
     * List of weather details.
     */
    private List<WeatherDetail> weather;

    /**
     * Entire JSON response.
     */
    private String rawData;

    /**
     * Represents detailed weather information.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDetail {

        /**
         * Weather description.
         */
        private String description;
    }
}
