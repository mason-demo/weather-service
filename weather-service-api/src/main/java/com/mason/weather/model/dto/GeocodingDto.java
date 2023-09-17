package com.mason.weather.model.dto;

import com.mason.weather.core.model.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for geocoding query details.
 *
 * @author: Mason
 * @date: 2023/9/16
 */

@Data
@Schema(description = "Geocoding query details")
public class GeocodingDto extends BaseDto {

    /**
     * City name
     */
    @Schema(description = "City name",
            example = "London")
    private String city;

    /**
     * Country code
     */
    @Schema(description = "Country code",
            example = "England")
    private String country;

}
