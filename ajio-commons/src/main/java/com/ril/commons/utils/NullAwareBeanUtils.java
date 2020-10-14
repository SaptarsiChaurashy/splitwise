package com.ril.commons.utils;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullAwareBeanUtils extends BeanUtilsBean {
  private String[] args;

  public NullAwareBeanUtils() {
  }

  public void copyProperties(Object dest, Object source) {
    try {
      super.copyProperties(dest, source);
    } catch (IllegalAccessException | InvocationTargetException var4) {
      throw new RuntimeException(var4.getMessage());
    }
  }

  public void copyProperties(Object dest, Object source, String... args) {
    try {
      this.args = args;
      this.copyProperties(dest, source);
    } catch (Exception var5) {
      throw new RuntimeException();
    }
  }

  public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
    if (value != null && !this.checkInArgs(name) && !value.equals("") && !value.equals("null")) {
      super.copyProperty(dest, name, value);
    }
  }

  private boolean checkInArgs(String name) {
    if (this.args == null) {
      return false;
    } else {
      String[] var2 = this.args;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
        String arg = var2[var4];
        if (arg.toLowerCase().contains(name.toLowerCase())) {
          return true;
        }
      }

      return false;
    }
  }
}
