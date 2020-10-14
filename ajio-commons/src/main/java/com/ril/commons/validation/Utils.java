package com.ril.commons.validation;

public class Utils {

  static boolean castBooleanWithDefault(Object v, boolean defaultV) {
    try {
      return ((Boolean) v);
    } catch (Exception e) {
      return defaultV;
    }
  }
}
