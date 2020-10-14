package com.ril.commons.validation;

public class ValidationException extends RuntimeException {

  private final ValidationReport report;

  public ValidationException(ValidationReport report) {
    super(report.toString());
    this.report = report;
  }
}
