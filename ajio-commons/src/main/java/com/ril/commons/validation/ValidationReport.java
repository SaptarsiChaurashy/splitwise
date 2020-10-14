package com.ril.commons.validation;

import java.util.List;

public class ValidationReport {
  private final boolean pass;
  private final String msg;
  private final List<String> errors;
  private final Object object;

  public ValidationReport(boolean pass, String msg, List<String> errors, Object object) {
    this.pass = pass;
    this.msg = msg;
    this.errors = errors;
    this.object = object;
  }

  public boolean isPass() {
    return pass;
  }
}
