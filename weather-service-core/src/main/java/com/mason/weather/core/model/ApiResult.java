package com.mason.weather.core.model;

import com.mason.weather.core.constants.ResultStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Common generic API response structure with fields for status, message, data, and details.
 *
 * @author: Mason
 * @date: 2023/9/16
 */

@Data
public class ApiResult<T> implements Serializable {

    /**
     * API response status.
     */
    @Schema(description = "Status of the request", example = "success")
    private String status;

    /**
     * Response code
     */
    @Schema(description = "Response code", example = "200")
    private int code;

    /**
     * Message regarding the status
     */
    @Schema(description = "Message regarding the status", example = "Request processed successfully")
    private String message;

    /**
     * API response data for successful requests.
     */
    private T data;

    /**
     * Additional details for error or warning responses.
     */
    @Schema(hidden = true)
    private T details;

    /**
     * Initializes the ApiResult.
     */
    private ApiResult(String status, int code, String message, T data, T details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.details = details;
    }

    /**
     * Returns a standard success response.
     */
    public static <T> ApiResult success() {
        return new ApiResult(ResultStatus.SUCCESS.value(), HttpStatus.OK.value(), null, null, null);
    }

    /**
     * Returns a success response with data.
     */
    public static <T> ApiResult success(T data) {
        return new ApiResult(ResultStatus.SUCCESS.value(), HttpStatus.OK.value(), null, data, null);
    }

    /**
     * Returns a default error response.
     */
    public static <T> ApiResult error() {
        return new ApiResult(ResultStatus.ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null, null);
    }

    /**
     * Returns an error response with a message.
     */
    public static <T> ApiResult error(int code, String message) {
        return new ApiResult(ResultStatus.ERROR.value(), code, message, null, null);
    }

    /**
     * Returns an error response with a message and details.
     */
    public static <T> ApiResult error(int code, String message, T detail) {
        return new ApiResult(ResultStatus.ERROR.value(), code, message, null, detail);
    }

}
