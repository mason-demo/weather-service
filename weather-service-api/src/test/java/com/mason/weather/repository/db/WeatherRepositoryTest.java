package com.mason.weather.repository.db;

import com.mason.weather.core.service.ApiKeyService;
import com.mason.weather.model.domain.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * WeatherRepository Test
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WeatherRepository weatherRepository;

    @MockBean
    private ApiKeyService apiKeyService;

    @BeforeEach
    void setUp() {
        Weather weather = new Weather();
        weather.setDescription("Sunny");
        weather.setFullCityName("London,England");
        entityManager.persist(weather);
    }

    /**
     * Test find weather entity by fullname
     */
    @Test
    void findByFullCityName() {
        Optional<Weather> result = weatherRepository.findByFullCityName("London,England");
        assertTrue(result.isPresent());
        assertEquals("Sunny", result.get().getDescription());
    }

}
