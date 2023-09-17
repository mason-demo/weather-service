package com.mason.weather.repository.api;

import com.mason.weather.model.dto.OpenWeatherDto;

/**
 * Interface for interacting with the OpenWeather API.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
public interface OpenWeatherClient {

    /**
     * Requests weather information for a specific city/country.
     *
     * @param fullCityName The complete name of the city.
     * @return Weather data retrieved for the city.
     */
    OpenWeatherDto requestWeatherByCity(String fullCityName);
}
