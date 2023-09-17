package com.mason.weather.service;

import com.mason.weather.core.exception.AppException;
import com.mason.weather.model.domain.Weather;
import com.mason.weather.model.dto.GeocodingDto;
import com.mason.weather.model.dto.OpenWeatherDto;
import com.mason.weather.model.vo.WeatherVo;
import com.mason.weather.repository.api.OpenWeatherClient;
import com.mason.weather.repository.db.WeatherRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the WeatherService interface.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @Override
    public WeatherVo getWeatherByCity(GeocodingDto geocodingDto) {
        String fullCityName = getFullCityName(geocodingDto);
        Weather weatherData = weatherRepository.findByFullCityName(fullCityName).orElseGet(() -> {
            OpenWeatherDto openWeatherDto = openWeatherClient.requestWeatherByCity(fullCityName);
            if (openWeatherDto == null) {
                throw new AppException(HttpStatus.NOT_FOUND, "The weather data cannot be found.");
            }
            Weather weather = new Weather();
            weather.setFullCityName(fullCityName);

            if (CollectionUtils.isEmpty(openWeatherDto.getWeather())
                    || openWeatherDto.getWeather().get(0) == null) {
                throw new AppException(HttpStatus.NOT_FOUND, "Weather details are missing");
            }
            weather.setDescription(openWeatherDto.getWeather().get(0).getDescription());
            weather.setRawData(openWeatherDto.getRawData());
            return weatherRepository.save(weather);
        });
        WeatherVo weatherVo = new WeatherVo(
                weatherData.getDescription(),
                weatherData.getCreatedTime(),
                weatherData.getUpdatedTime()
        );
        return weatherVo;
    }

    /**
     * Constructs the full city name based on the provided geocoding details.
     *
     * @param geocodingDto The geocoding information
     * @return The full name of the city.
     */
    private String getFullCityName(GeocodingDto geocodingDto) {
        List<String> parts = new ArrayList<>();

        if (StringUtils.isNotEmpty(geocodingDto.getCity())) {
            parts.add(geocodingDto.getCity());
        }
        if (StringUtils.isNotEmpty(geocodingDto.getCountry())) {
            parts.add(geocodingDto.getCountry());
        }
        String fullCityName = String.join(",", parts);
        return fullCityName;
    }
}
