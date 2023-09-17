package com.mason.weather.core.constants;

/**
 * Defines various result status.
 *
 * @author: Mason
 * @date: 2023/9/16
 */
public enum ResultStatus {

    /**
     * Indicates a successful status.
     */
    SUCCESS("success"),

    /**
     * Indicates an error status.
     */
    ERROR("error"),

    /**
     * Indicates a pending status.
     */
    PENDING("pending"),

    /**
     * Indicates a warning status.
     * Sometime, returns data with a warning.
     */
    WARNING("warning");

    /**
     * Result status
     */
    private final String value;

    /**
     * Initialize the status with a value.
     *
     * @param value The status value.
     */
    ResultStatus(String value) {
        this.value = value;
    }

    /**
     * Fetch the status value.
     *
     * @return The status value.
     */
    public String value() {
        return value;
    }
}
