package com.mason.weather.controller;

import com.mason.weather.core.mocking.MockApiKeys;
import com.mason.weather.core.service.ApiKeyService;
import com.mason.weather.model.dto.GeocodingDto;
import com.mason.weather.model.vo.WeatherVo;
import com.mason.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WeatherController Test
 * Isolate WeatherService and ApiKeyService
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private ApiKeyService apiKeyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * A request with valid apikey, city and country data
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_withValidCity_returnsWeather() throws Exception {
        when(apiKeyService.isValidApiKey(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(apiKeyService.isWithinRateLimit(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(weatherService.getWeatherByCity(any(GeocodingDto.class))).thenReturn(new WeatherVo("Sunny day", new Date(), new Date()));

        mockMvc.perform(get("/api/weather")
                        .param("apikey", MockApiKeys.API_KEY_1.value())
                        .param("city", "London")
                        .param("country", "England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.description").value("Sunny day"));
    }

    /**
     * A request with empty apikey
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_withEmptyApiKey_throwsAppException() throws Exception {
        mockMvc.perform(get("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("API Key is mandatory"));
    }

    /**
     * A request with invalid apikey
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_withInvalidApiKey_throwsAppException() throws Exception {
        String fake_api_key = "fake_api_key";
        when(apiKeyService.isValidApiKey(fake_api_key)).thenReturn(false);
        mockMvc.perform(get("/api/weather")
                        .param("apikey", fake_api_key)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid API Key"));
    }

    /**
     * A request reaches apikey rate limit
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_reachApiKeyLimit_throwsAppException() throws Exception {
        when(apiKeyService.isValidApiKey(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(apiKeyService.isWithinRateLimit(MockApiKeys.API_KEY_1.value())).thenReturn(false);
        mockMvc.perform(get("/api/weather")
                        .param("apikey", MockApiKeys.API_KEY_1.value())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.message").value("API Key Rate Limit Exceeded"));
    }

    /**
     * A request with valid apikey, but missing BOTH city AND country
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_missingBothCityAndCountry_throwsAppException() throws Exception {
        when(apiKeyService.isValidApiKey(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(apiKeyService.isWithinRateLimit(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        mockMvc.perform(get("/api/weather")
                        .param("apikey", MockApiKeys.API_KEY_1.value())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("At least one of 'city' or 'country' must have a value"));
    }

    /**
     * A request with valid apikey, but missing EITHER city OR country
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_missingCityOrCountry_returnsWeather1() throws Exception {
        when(apiKeyService.isValidApiKey(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(apiKeyService.isWithinRateLimit(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(weatherService.getWeatherByCity(any(GeocodingDto.class))).thenReturn(new WeatherVo("Sunny day", new Date(), new Date()));

        mockMvc.perform(get("/api/weather")
                        .param("apikey", MockApiKeys.API_KEY_1.value())
                        .param("city", "London")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").exists());
    }

    /**
     * A request with valid apikey, but missing EITHER city OR country
     *
     * @throws Exception
     */
    @Test
    void getWeatherByCity_missingCityOrCountry_returnsWeather2() throws Exception {
        when(apiKeyService.isValidApiKey(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(apiKeyService.isWithinRateLimit(MockApiKeys.API_KEY_1.value())).thenReturn(true);
        when(weatherService.getWeatherByCity(any(GeocodingDto.class))).thenReturn(new WeatherVo("Sunny day", new Date(), new Date()));

        mockMvc.perform(get("/api/weather")
                        .param("apikey", MockApiKeys.API_KEY_1.value())
                        .param("country", "England")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").exists());
    }

}
