package com.mason.weather.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mason.weather.core.exception.AppException;
import com.mason.weather.model.dto.OpenWeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Implementation of the OpenWeatherClient, making requests to the OpenWeather API.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
@Service
@Slf4j
public class OpenWeatherClientImpl implements OpenWeatherClient {

    @Value("${api.openweather.base-url}")
    private String BASE_URL;

    @Value("${api.openweather.endpoint}")
    private String WEATHER_ENDPOINT;

    @Value("${api.openweather.api-key}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    /**
     * Fetches weather data for a given city from OpenWeather API.
     *
     * @param fullCityName The name of the city.
     * @return Weather data for the city.
     */
    public OpenWeatherDto requestWeatherByCity(String fullCityName) {
        URI uri = buildUri(fullCityName);
        try {
            String responseStr = restTemplate.getForObject(uri, String.class);
            OpenWeatherDto dto = objectMapper.readValue(responseStr, OpenWeatherDto.class);
            dto.setRawData(responseStr);
            return dto;
        } catch (HttpClientErrorException ex) {
            handleHttpClientErrorException(ex);
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse JSON: {}", ex.getMessage());
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Remote service interaction failed.");
        }
        return null;
    }

    /**
     * Handles HTTP errors when calling the OpenWeather service.
     */
    private static void handleHttpClientErrorException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new AppException(HttpStatus.NOT_FOUND, "City cannot be found");
        } else {
            log.error("Remote OpenWeather service error: {}", ex.getMessage());
            throw new AppException(HttpStatus.SERVICE_UNAVAILABLE, "Remote service is unavailable");
        }
    }

    /**
     * Constructs the URI for fetching weather data.
     */
    private URI buildUri(String fullCityName) {
        return UriComponentsBuilder.fromUriString(BASE_URL + WEATHER_ENDPOINT)
                .queryParam("q", fullCityName)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();
    }

}
