package com.mason.weather.core.model.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents an API key entity stored in the DB.
 *
 * @author: Mason
 * @date: 2023/9/17
 */

@Data
@Entity
public class ApiKey {

    /**
     * Primary key with auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * apikey column
     */
    @Column(nullable = false)
    private String apikey;

}
