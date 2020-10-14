package com.ril.commons.response;

/** SuccessResponse if the low level api call was successful. Created by siddhants on 7/11/16. */
public final class SuccessResponse<T> extends Response<T> {

  public SuccessResponse(T data) {
    super(1, data);
  }
}
