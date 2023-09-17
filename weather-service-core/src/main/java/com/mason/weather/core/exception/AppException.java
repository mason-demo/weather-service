package com.mason.weather.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Custom exception to handle application-specific errors with details.
 *
 * @author: Mason
 * @date: 2023/9/17
 */

@Data
public class AppException extends RuntimeException {

    /**
     * The relevant HTTP status for the exception.
     */
    private HttpStatus httpStatus;

    /**
     * The detail message for the exception.
     */
    private String message;

    /**
     * Additional details about the exception.
     */
    private Object details;

    /**
     * Constructs a new AppException with httpStatus.
     *
     * @param httpStatus
     */
    public AppException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.getReasonPhrase(), null);
    }

    /**
     * Constructs a new AppException with httpStatus and message
     *
     * @param httpStatus
     * @param message
     */
    public AppException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    /**
     * Constructs a new AppException with httpStatus, message and exception details
     *
     * @param httpStatus
     * @param message
     * @param details
     */
    public AppException(HttpStatus httpStatus, String message, Object details) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
    }
}
