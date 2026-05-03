package com.lockerdelivery.common.exception.controller;

import com.lockerdelivery.common.exception.ErrorCode;
import com.lockerdelivery.common.exception.ErrorResponse;
import com.lockerdelivery.common.exception.LockerException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST API error responses. Provides consistent error response structure across all
 * endpoints.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(LockerException.class)
  public ResponseEntity<ErrorResponse> handleDomainException(
      LockerException ex,
      HttpServletRequest request) {
    log.warn("Locker error: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
    ErrorResponse error = new ErrorResponse(ex.getErrorCode(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(error);
  }

  /**
   * Handles Spring validation errors from @Valid annotations.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    Map<String, String> validationErrors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      validationErrors.put(fieldName, errorMessage);
    });

    log.warn("Validation failed: {}", validationErrors);
    ErrorResponse error = new ErrorResponse(
        ErrorCode.VALIDATION_ERROR,
        "Request validation failed",
        request.getRequestURI(),
        validationErrors
    );
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(error);
  }

  /**
   * Handles missing or malformed request body.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpServletRequest request) {
    log.warn("Invalid request body: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "Request body is missing or malformed",
        request.getRequestURI());
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(error);
  }

  /**
   * Handles missing required request parameters.
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpServletRequest request) {
    log.warn("Missing required parameter: {}", ex.getParameterName());
    ErrorResponse error = new ErrorResponse(
        ErrorCode.VALIDATION_ERROR,
        "Required parameter '" + ex.getParameterName() + "' is missing",
        request.getRequestURI()
    );
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(error);
  }

  /**
   * Handles illegal state exceptions.
   */
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> handleIllegalState(
      IllegalStateException ex,
      HttpServletRequest request) {
    log.warn("Illegal state: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(ErrorCode.INVALID_OPERATION, ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(ErrorCode.INVALID_OPERATION.getHttpStatus()).body(error);
  }

  /**
   * Handles illegal argument exceptions.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(
      IllegalArgumentException ex,
      HttpServletRequest request) {
    log.warn("Invalid argument: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse(ErrorCode.INVALID_ARGUMENT, ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(ErrorCode.INVALID_ARGUMENT.getHttpStatus()).body(error);
  }

  /**
   * Fallback handler for any unhandled exceptions.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericError(
      Exception ex,
      HttpServletRequest request) {
    log.error("Unexpected error occurred", ex);
    ErrorResponse error = new ErrorResponse(
        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
        ErrorCode.INTERNAL_SERVER_ERROR.getDetails(),
        ErrorCode.INTERNAL_SERVER_ERROR.getDetails(),
        request.getRequestURI(),
        null,
        java.time.Instant.now()
    );
    return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(error);
  }
}
