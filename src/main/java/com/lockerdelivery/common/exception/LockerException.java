package com.lockerdelivery.common.exception;

public class LockerException extends RuntimeException{

  private final ErrorCode errorCode;

  public LockerException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public LockerException(ErrorCode errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
