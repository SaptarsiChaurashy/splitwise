package com.ril.commons.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class Response<T> extends ResponseEntity<T> {
  // Integer status code for the response
  private int code;
  /** Status Message complimenting statusCode */
  private String statusMessage;
  public Response() {
    super(HttpStatus.OK);
  }
  public Response(T data, int code, String statusMessage) {
    super(data, HttpStatus.OK);
    init(code, statusMessage);
  }
  public Response(HttpStatus httpStatus, T data, int code, String statusMessage) {
    super(data, httpStatus);
    init(code, statusMessage);
  }

  private void init(int code, String statusMessage) {
    this.setCode(code);
    this.setStatusMessage(statusMessage);
  }

  public Response(HttpStatus httpStatus, int code, String statusMessage) {
    super(httpStatus);
    init(code, statusMessage);
  }

  public Response(HttpStatus httpStatus, int code, T data) {
    super(httpStatus);
    this.setCode(code);
    init(code, "");
  }
  /**
   * @param statusCode Integer code of the response
   * @param statusMessage String message of the response
   */
  public Response(int statusCode, String statusMessage) {
    super(HttpStatus.OK);
    init(statusCode, statusMessage);
  }

  public Response(int statusCode, T data) {
    super(data, HttpStatus.OK);
    init(statusCode, "");
  }

  public Response(HttpStatus httpStatus) {
    super(httpStatus);
  }

  public static class ResponseBuilder<T> {
    private T data;
    private int code;
    private String message;
    private HttpStatus httpStatus;

    ResponseBuilder(HttpStatus httpStatus) {
      this.httpStatus = httpStatus;
    }
    public ResponseBuilder<T> body(T data) {
      this.data = data;
      return this;
    }
    public ResponseBuilder<T> code(int code) {
      this.code = code;
      return this;
    }
    public ResponseBuilder<T> message(String message) {
      this.message = message;
      return this;
    }
    public Response<T> build() {
      return new Response<>(httpStatus, data, code, message);
    }
  }

  public static <L> ResponseBuilder<L> builder(HttpStatus httpStatus){
    return new ResponseBuilder<>(httpStatus);
  }
  public static <L> ResponseBuilder<L> builder(){
    return new ResponseBuilder<>(HttpStatus.OK);
  }
}
