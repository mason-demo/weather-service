package com.mason.weather.repository.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mason.weather.core.exception.AppException;
import com.mason.weather.model.dto.OpenWeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * OpenWeatherClient Test
 *
 * @author: Mason
 * @date: 2023/9/17
 */
public class OpenWeatherClientTest {

    @InjectMocks
    private OpenWeatherClientImpl client;

    @Mock
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper(); // Real instance instead of a mock

    private final String testCityName = "TestCity";
    private final String testApiResponse = "{\"data\": \"mocked data\"}";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Get weather data success
     * @throws Exception
     */
    @Test
    public void testRequestWeatherByCitySuccess() throws Exception {
        OpenWeatherDto mockDto = new OpenWeatherDto();
        when(restTemplate.getForObject(any(URI.class), eq(String.class))).thenReturn(testApiResponse);

        OpenWeatherDto result = client.requestWeatherByCity(testCityName);

        assertNotNull(result);
        assertEquals(testApiResponse, result.getRawData());
    }

    /**
     * No weather data found.
     */
    @Test
    public void testRequestWeatherByCityNotFound() {
        when(restTemplate.getForObject(any(URI.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(AppException.class, () -> client.requestWeatherByCity(testCityName));
    }

}
