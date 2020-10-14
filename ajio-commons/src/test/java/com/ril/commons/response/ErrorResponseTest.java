package com.ril.commons.response;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ErrorResponseTest {

  @Test
  public void testEmptyConstructor() {
    ErrorResponse errorResponse = new ErrorResponse();
    Assert.assertEquals(errorResponse.getStatusMessage(), "error");
    Assert.assertTrue(errorResponse.getCode() == 0);
  }

  @Test
  public void testStatusMessageConstructor() {
    ErrorResponse errorResponse = new ErrorResponse("Error Message");
    Assert.assertEquals(errorResponse.getStatusMessage(), "Error Message");
    Assert.assertTrue(errorResponse.getCode() == 0);
  }

  @Test
  public void testDataConstructor() {
    ErrorResponse<BigDecimal> errorResponse = new ErrorResponse<>(new BigDecimal(10));
    Assert.assertEquals(errorResponse.getBody(), new BigDecimal(10));
    Assert.assertTrue(errorResponse.getCode() == 0);
  }
}
