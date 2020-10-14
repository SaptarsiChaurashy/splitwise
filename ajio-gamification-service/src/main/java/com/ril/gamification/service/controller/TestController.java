package com.ril.gamification.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
public class TestController {

  @GetMapping("/api")
  public String okApi(){
    return "OK";
  }
}
