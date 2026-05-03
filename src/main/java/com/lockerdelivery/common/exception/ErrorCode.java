package com.lockerdelivery.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Standard error codes for service. Each error code contains: - code: A unique string identifier for the error -
 * httpStatus: The HTTP status code to return - details: A description of the error
 */
public enum ErrorCode {

  RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND,
      "The requested resource was not found"),

  VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.BAD_REQUEST,
      "Request validation failed"),

  INVALID_ARGUMENT("INVALID_ARGUMENT", HttpStatus.BAD_REQUEST,
      "Invalid argument provided"),

  INVALID_OPERATION("INVALID_OPERATION", HttpStatus.BAD_REQUEST,
      "The requested operation is not valid"),

  // Conflict errors
  DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", HttpStatus.CONFLICT,
      "A resource with the same identifier already exists"),

  INVALID_STATE("INVALID_STATE", HttpStatus.CONFLICT,
      "The resource is in an invalid state for this operation"),


  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,
      "An unexpected error occurred. Please try again later");

  private final String code;
  private final HttpStatus httpStatus;
  private final String details;

  ErrorCode(String code, HttpStatus httpStatus, String details) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.details = details;
  }

  public String getCode() {
    return code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getDetails() {
    return details;
  }
}
