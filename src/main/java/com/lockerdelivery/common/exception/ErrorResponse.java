package com.lockerdelivery.common.exception;

import java.time.Instant;
import java.util.Map;

/**
 * Standard error response structure for REST APIs.
 */
public record ErrorResponse(
    String code,
    String message,
    String details,
    String path,
    Map<String, String> validationErrors,
    Instant timestamp
) {
    public ErrorResponse(String code, String message) {
        this(code, message, null, null, null, Instant.now());
    }

    public ErrorResponse(String code, String message, String path) {
        this(code, message, null, path, null, Instant.now());
    }

    public ErrorResponse(ErrorCode errorCode, String message, String path) {
        this(errorCode.getCode(), message, errorCode.getDetails(), path, null, Instant.now());
    }

    public ErrorResponse(ErrorCode errorCode, String message, String path, Map<String, String> validationErrors) {
        this(errorCode.getCode(), message, errorCode.getDetails(), path, validationErrors, Instant.now());
    }

    public ErrorResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getDetails(), errorCode.getDetails(), null, null, Instant.now());
    }
}
