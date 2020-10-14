package com.ril.commons.utils;

import com.ril.commons.response.ErrorResponse;
import com.ril.commons.response.Response;
import com.ril.commons.response.SuccessResponse;
import com.ril.commons.trywith.Failure;
import com.ril.commons.trywith.Success;
import com.ril.commons.trywith.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class TryUtils {

  public static <T> Response<?> tryToResponse(Try<T> tTry) {
    return tTry.map(SuccessResponse::new).recover(TryUtils::buildErrorResponse).get();
  }

  public static <T> Response<?> tryToResponseGeneric(Try<T> tTry, String successMessage) {
    return tTry.map(
            obj -> {
              Response.ResponseBuilder<Object> r = Response.builder();
              r.message(successMessage);
              r.code(1);
              r.body(obj);
              return r.build();
            })
        .recover(
            err -> {
              Response.ResponseBuilder<Object> r = Response.builder(HttpStatus.BAD_REQUEST);
              log.error("{}", err);
              r.code(0);
              r.body(new ErrorResponse<>(HttpStatus.BAD_REQUEST, 0, err.getMessage()));
              return r.build();
            })
        .get();
  }

  public static <T> Try<List<T>> flatten(List<Try<T>> list) {
    return list.stream()
        .filter(Try::isFailure)
        .findAny()
        .map(f -> (Try<List<T>>) f)
        .orElseGet(
            () ->
                new Success<List<T>>(list.stream().map(t -> t.get()).collect(Collectors.toList())));
  }

  public static <T> Try<T> peek(Predicate<T> pred, Throwable ex, T t) {
    if (!pred.test(t)) return new Failure<>(ex);
    return new Success<>(t);
  }

  private static ErrorResponse<?> buildErrorResponse(Throwable throwable) {
    log.error("{}", throwable);
    return new ErrorResponse<>(throwable.getMessage());
  }
}
