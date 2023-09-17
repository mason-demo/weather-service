package com.mason.weather.core.exception;

import com.mason.weather.core.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles global exceptions across the application.
 *
 * @author: Mason
 * @date: 2023/9/16
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions.
     *
     * @param ex The exception instance.
     * @return ResponseEntity with validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, Object> details = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            details.put(field, message);
        });
        ApiResult result = ApiResult.error(HttpStatus.BAD_REQUEST.value(), "The requested parameters are incorrect", details);
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions from failed Redis connections.
     *
     * @param ex The exception instance.
     * @return ResponseEntity with the error details.
     */
    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ApiResult> handleRedisConnectionFailureException(
            RedisConnectionFailureException ex
    ) {
        log.error("Unable to connect to Redis: {}", ex.getCause());
        ApiResult result = ApiResult.error(HttpStatus.SERVICE_UNAVAILABLE.value(), "Unable to connect to Redis");
        return new ResponseEntity(result, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Handles runtime exceptions.
     *
     * @param ex The exception instance.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResult> handleRuntimeException(RuntimeException ex) {
        ApiResult result = ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        log.error("An unexpected error occurred", ex);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles customized AppException
     *
     * @param ex The exception instance.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResult> handleAppException(AppException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        String message = ex.getMessage();
        Object details = ex.getDetails();
        ApiResult result = ApiResult.error(httpStatus.value(), message, details);
        return new ResponseEntity<>(result, httpStatus);
    }

}
