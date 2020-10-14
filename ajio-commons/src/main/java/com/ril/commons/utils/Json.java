package com.ril.commons.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;

public class Json {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public Json() {}

  public static <T> String serialize(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (IOException var2) {
      throw new IllegalArgumentException("Cannot serialize given object");
    }
  }

  public static <T> T deserialize(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException var3) {
      throw new IllegalArgumentException("Cannot deserialize given object");
    }
  }

  public static <T> T deserialize(String json, TypeReference<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (IOException var3) {
      throw new IllegalArgumentException("Cannot deserialize given object");
    }
  }

  static {
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }
}
