package com.mason.weather.controller;

import com.mason.weather.core.exception.AppException;
import com.mason.weather.core.model.ApiResult;
import com.mason.weather.model.dto.GeocodingDto;
import com.mason.weather.model.vo.WeatherVo;
import com.mason.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Weather Service Restful Controller
 *
 * @author: Mason
 * @date: 2023/9/15
 */
@RestController()
@RequestMapping("/api")
@Tag(name = "Geocoding API", description = "Get weather information through geographic information.")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Fetches weather details using provided geographic info.
     *
     * @param geocodingDto Geographic details for the query.
     * @return Weather details wrapped in an ApiResult.
     */
    @GetMapping("/weather")
    @Operation(summary = "Get weather by city name or/and country name.")
    public ResponseEntity<ApiResult<WeatherVo>> getWeatherByCity(
            @Validated @ModelAttribute GeocodingDto geocodingDto
    ) {
        if (StringUtils.isEmpty(geocodingDto.getCity())
                && StringUtils.isEmpty(geocodingDto.getCountry())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "At least one of 'city' or 'country' must have a value");
        }
        WeatherVo data = weatherService.getWeatherByCity(geocodingDto);
        ApiResult apiResult = ApiResult.success(data);
        return ResponseEntity.ok(apiResult);
    }

}
