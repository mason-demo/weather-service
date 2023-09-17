package com.mason.weather.core.service;

import com.mason.weather.core.model.domain.ApiKey;

/**
 * This interface outlines methods for verifying the validity and rate limits of API keys
 *
 * @author: Mason
 * @date: 2023-09-18
 */
public interface ApiKeyService {

    /**
     * Validates the provided API key.
     *
     * @param apikey The API key to validate.
     * @return true if valid, else false.
     */
    boolean isValidApiKey(String apikey);

    /**
     * Checks rate limits for the given API key.
     *
     * @param apikey The API key to check.
     * @return true if within limits, else false.
     */
    boolean isWithinRateLimit(String apikey);

    /**
     * Saves the given API key entity.
     *
     * @param apiKey The API key to save.
     */
    void save(ApiKey apiKey);

}
