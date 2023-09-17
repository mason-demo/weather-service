package com.mason.weather.service;

import com.mason.weather.core.exception.AppException;
import com.mason.weather.model.domain.Weather;
import com.mason.weather.model.dto.GeocodingDto;
import com.mason.weather.model.dto.OpenWeatherDto;
import com.mason.weather.model.vo.WeatherVo;
import com.mason.weather.repository.api.OpenWeatherClient;
import com.mason.weather.repository.db.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * WeatherService Test
 *
 * @author: Mason
 * @date: 2023/9/17
 */
class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private OpenWeatherClient openWeatherClient;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Get Weather Data from DB
     */
    @Test
    void getWeatherByCity_fromDatabase() {
        String cityName = "London";
        String countryName = "England";
        String fullCityName = cityName + "," + countryName;

        GeocodingDto geoDto = new GeocodingDto();
        geoDto.setCity(cityName);
        geoDto.setCountry(countryName);

        Weather weather = new Weather();
        weather.setDescription("Sunny");

        when(weatherRepository.findByFullCityName(fullCityName)).thenReturn(Optional.of(weather));

        WeatherVo result = weatherService.getWeatherByCity(geoDto);

        assertNotNull(result);
        assertEquals("Sunny", result.description());

        // openWeatherClient is not called as expected
        verify(openWeatherClient, never()).requestWeatherByCity(anyString());
    }

    /**
     * Get Weather Data from OpenWeather API
     */
    @Test
    void getWeatherByCity_fromOpenWeatherClient() {
        String cityName = "London";
        String countryName = "England";
        String fullCityName = cityName + "," + countryName;

        GeocodingDto dto = new GeocodingDto();
        dto.setCity(cityName);
        dto.setCountry(countryName);

        OpenWeatherDto openWeatherDto = new OpenWeatherDto();
        OpenWeatherDto.WeatherDetail weatherDetail = new OpenWeatherDto.WeatherDetail();
        weatherDetail.setDescription("Sunny");
        openWeatherDto.setWeather(List.of(weatherDetail));
        openWeatherDto.setRawData("rawData");

        Weather weather = new Weather();
        weather.setDescription(weatherDetail.getDescription());

        when(weatherRepository.findByFullCityName(fullCityName)).thenReturn(Optional.empty());
        when(openWeatherClient.requestWeatherByCity(fullCityName)).thenReturn(openWeatherDto);
        when(weatherRepository.save(any(Weather.class))).thenReturn(weather);

        WeatherVo result = weatherService.getWeatherByCity(dto);

        assertNotNull(result);
        assertEquals("Sunny", result.description());
    }

    /**
     * Get null Data from OpenWeather API
     */
    @Test
    void getWeatherByCity_openWeatherClientReturnsNull() {
        String cityName = "London";
        String countryName = "England";
        String fullCityName = cityName + "," + countryName;

        GeocodingDto dto = new GeocodingDto();
        dto.setCity(cityName);
        dto.setCountry(countryName);

        when(weatherRepository.findByFullCityName(fullCityName)).thenReturn(Optional.empty());
        when(openWeatherClient.requestWeatherByCity(fullCityName)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> {
            weatherService.getWeatherByCity(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("The weather data cannot be found.", exception.getMessage());

        // Ensure that the save method wasn't called
        verify(weatherRepository, never()).save(any(Weather.class));
    }

    /**
     * Get Empty Data(missing description) from OpenWeather API
     */
    @Test
    void getWeatherByCity_weatherDetailsMissingInDto() {
        String cityName = "London";
        String countryName = "England";
        String fullCityName = cityName + "," + countryName;

        GeocodingDto dto = new GeocodingDto();
        dto.setCity(cityName);
        dto.setCountry(countryName);

        OpenWeatherDto openWeatherDto = new OpenWeatherDto();

        when(weatherRepository.findByFullCityName(fullCityName)).thenReturn(Optional.empty());
        when(openWeatherClient.requestWeatherByCity(fullCityName)).thenReturn(openWeatherDto);

        AppException exception = assertThrows(AppException.class, () -> {
            weatherService.getWeatherByCity(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Weather details are missing", exception.getMessage());

        // Ensure that the save method wasn't called
        verify(weatherRepository, never()).save(any(Weather.class));
    }

}
