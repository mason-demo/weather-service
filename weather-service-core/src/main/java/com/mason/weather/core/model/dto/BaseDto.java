package com.mason.weather.core.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Base Dto containing the API key.
 *
 * @author: Mason
 * @date: 2023/9/16
 */

@Data
public class BaseDto {

    /**
     * The unique API key.
     */
    @Schema(description = "Your unique API key",
            example = "api_key_1")
    @NotBlank(message = "API Key is mandatory")
    private String apikey;

}
