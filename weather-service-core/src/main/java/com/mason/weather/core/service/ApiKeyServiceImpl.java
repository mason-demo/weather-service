package com.mason.weather.core.service;

import com.mason.weather.core.model.domain.ApiKey;
import com.mason.weather.core.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the ApiKeyService
 *
 * @author: Mason
 * @date: 2023/9/17
 */
@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    /**
     * apikey max limit rate
     */
    @Value("${apikey.rate.max}")
    private int APIKEY_MAX_REQUEST;

    /**
     * apikey limit rate interval in hour
     */
    @Value("${apikey.rate.interval}")
    private int APIKEY_RESET_INTERVAL;

    /**
     * The apikey refresh interval in hour
     */
    @Value("${apikey.refresh.interval}")
    private int APIKEY_REFRESH_INTERVAL;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean isValidApiKey(String apikey) {
        // Check Redis first
        Boolean hasKey = stringRedisTemplate.hasKey(apikey); // Using stringRedisTemplate
        if (hasKey != null && hasKey) {
            return true;
        }

        // Check DB if not found Redis
        Optional<ApiKey> keyFromDb = apiKeyRepository.findByApikey(apikey);
        if (keyFromDb.isPresent()) {
            // Store in Redis for future quick checks
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue(); // Using stringRedisTemplate
            opsForValue.set(apikey, "valid", APIKEY_REFRESH_INTERVAL, TimeUnit.HOURS);
            return true;
        }

        return false;
    }

    @Override
    public boolean isWithinRateLimit(String apikey) {
        String rateLimitKey = apikey + ":rateLimit";

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue(); // Using stringRedisTemplate

        Long count = opsForValue.increment(rateLimitKey); // This returns a Long by default

        // When the count is 1, this is the first request in the current time window.
        if (count == 1) {
            stringRedisTemplate.expire(rateLimitKey, APIKEY_RESET_INTERVAL, TimeUnit.HOURS); // Using stringRedisTemplate
        }

        // If count exceeds the max request, the API key has exceeded the rate limit.
        return count != null && count <= APIKEY_MAX_REQUEST;
    }

    @Override
    public void save(ApiKey apiKey) {
        apiKeyRepository.save(apiKey);
    }

}
