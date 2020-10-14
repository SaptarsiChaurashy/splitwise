package com.ril.commons.response;

import org.junit.Assert;
import org.junit.Test;

public class SuccessResponseTest {

  @Test
  public void testSuccessResponseWithData() {
    SuccessResponse<Double> successResponse = new SuccessResponse<>(12.00);
    Assert.assertTrue(successResponse.getBody() == 12.00);
    Assert.assertTrue(successResponse.getCode() == 1);
  }
}
