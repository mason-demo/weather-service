package com.mason.weather.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Represents the result of weather information.
 *
 * @author: Mason
 * @date: 2023-09-16
 */
@Schema(description = "Weather Result")
public record WeatherVo(

        /**
         * Weather description.
         */
        @Schema(description = "Weather description", example = "overcast clouds")
        String description,

        /**
         * Creation timestamp.
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Creation timestamp", example = "2023-09-16 18:07:52")
        Date createdTime,

        /**
         * Updated timestamp.
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Updated timestamp", example = "2023-09-16 18:07:52")
        Date updatedTime
) {
}
