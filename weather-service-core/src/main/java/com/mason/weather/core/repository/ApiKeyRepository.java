package com.mason.weather.core.repository;

import com.mason.weather.core.model.domain.ApiKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * TODO_DESCRIPTION
 *
 * @author: Mason
 * @date: 2023-09-17
 */
@Repository
public interface ApiKeyRepository extends CrudRepository<ApiKey, Long> {

    /**
     * Finds an API key entity by its value.
     *
     * @param apikey The API key value to search.
     * @return Optional of ApiKey if found, otherwise empty.
     */
    Optional<ApiKey> findByApikey(String apikey);

}
