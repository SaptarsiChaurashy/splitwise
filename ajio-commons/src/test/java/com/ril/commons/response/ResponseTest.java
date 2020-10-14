package com.ril.commons.response;

import org.junit.Assert;
import org.junit.Test;

public class ResponseTest {

  @Test
  public void testStatusCodeStatusMessageConstructor() {
    Response response = new Response(1, "Status Message");
    Assert.assertEquals(response.getStatusMessage(), "Status Message");
    Assert.assertEquals(response.getCode(), 1);
  }

  @Test
  public void testStatusCodeDataConstructor() {
    Response<Long> response = new Response<>(1, 20L);
    Assert.assertEquals(response.getCode(), 1);
    Assert.assertTrue(response.getBody() == 20L);
  }

  @Test
  public void testAllArgsConstructor() {
    Response<Double> response = new Response<Double>(12.2, 1, "Status");
    Assert.assertEquals(response.getStatusMessage(), "Status");
    Assert.assertEquals(response.getCode(), 1);
    Assert.assertTrue(response.getBody() == 12.2);
  }

  @Test
  public void testBuilder() {
    Response<Long> response = Response
            .<Long>builder()
            .body(11L)
            .code(2)
            .message("Status")
            .build();
    Assert.assertEquals(response.getStatusMessage(), "Status");
    Assert.assertEquals(response.getCode(), 2);
    Assert.assertTrue(response.getBody() == 11L);
  }
}
