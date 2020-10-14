package com.ril.commons.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

  static Object callMethod(Method m, Object o) {
    try {
      return m.invoke(null, o);
    } catch (InvocationTargetException | IllegalAccessException e) { // Warning todo
      e.printStackTrace();
    }
    return null;
  }
}
