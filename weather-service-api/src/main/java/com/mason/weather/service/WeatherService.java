package com.mason.weather.service;

import com.mason.weather.model.dto.GeocodingDto;
import com.mason.weather.model.vo.WeatherVo;

/**
 * Service interface for fetching weather details.
 *
 * @author: Mason
 * @date: 2023-09-16
 */
public interface WeatherService {

    /**
     * Fetches weather details using provided geographic info.
     *
     * @param geocodingDto Geographic details for the query.
     * @return A map containing weather details.
     */
    WeatherVo getWeatherByCity(GeocodingDto geocodingDto);

}
