package com.ril.gamification.service.controller;

import org.slf4j.MDC;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Controller {

  default void setMdc(Map<String, String> headerMap, String... args) {
    MDC.put(
        "REQUEST_ID",
        Optional.ofNullable(headerMap.get("x_request_id"))
            .orElseGet(() -> UUID.randomUUID().toString()));
  }
}
