package com.ril.commons.utils;

public class ObjectUtils {
  public ObjectUtils() {}

  public static <T> T deepCopy(T object) {
    return (T) Json.deserialize(Json.serialize(object), object.getClass());
  }
}
