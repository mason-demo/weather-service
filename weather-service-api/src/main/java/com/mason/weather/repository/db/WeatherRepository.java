package com.mason.weather.repository.db;

import com.mason.weather.model.domain.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for DB operations on the Weather domain entity.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
public interface WeatherRepository extends CrudRepository<Weather, Long> {

    /**
     * Finds a Weather entry by its full city name.
     *
     * @param fullCityName The full name of the city.
     * @return An Optional containing the Weather entry.
     */
    Optional<Weather> findByFullCityName(String fullCityName);

}
