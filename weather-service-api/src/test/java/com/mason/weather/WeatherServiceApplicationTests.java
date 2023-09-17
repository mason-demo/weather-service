package com.mason.weather;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the WeatherServiceApiApplication.
 *
 * @author: Mason
 * @date: 2023-09-17
 */
@SpringBootTest
class WeatherServiceApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
		assertThat(restTemplate).isNotNull();

		assertThat(stringRedisTemplate).isNotNull();

		assertThat(entityManager).isNotNull();
	}

}
