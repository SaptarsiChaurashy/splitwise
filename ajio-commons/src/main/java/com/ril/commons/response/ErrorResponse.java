package com.ril.commons.response;

import org.springframework.http.HttpStatus;

/** Created by siddhants on 7/11/16. */
public final class ErrorResponse<T> extends Response<T> {

  /** @param message */
  public ErrorResponse(String message) {
    super(0, message);
  }

  public ErrorResponse(int code, String message) {
    super(code, message);
  }

  public ErrorResponse(HttpStatus httpStatus, int code, String message) {
    super(httpStatus, code, message);
  }

  /** @param message */
  public ErrorResponse(T message) {
    super(0, message);
  }

  /** Generic Error Response in case no input is given */
  public ErrorResponse() {
    super(0, "error");
  }
}
