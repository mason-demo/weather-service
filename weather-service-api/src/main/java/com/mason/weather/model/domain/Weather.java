package com.mason.weather.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents weather data for a city.
 * <p>
 * * @author: Mason
 * * @date: 2023/9/16
 */
@Data
@Entity
public class Weather {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * City name.
     */
    @Column(nullable = false)
    private String fullCityName;

    /**
     * Weather description.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Detailed weather data.
     */
    @Column(length = 2000)
    private String rawData;

    /**
     * Creation timestamp.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * Update timestamp.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    /**
     * Sets timestamps before persisting.
     */
    @PrePersist
    public void onPrePersist() {
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    /**
     * Updates timestamp before updating.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.updatedTime = new Date();
    }

}
