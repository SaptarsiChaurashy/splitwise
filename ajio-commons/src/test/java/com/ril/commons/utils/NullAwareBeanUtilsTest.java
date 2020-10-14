package com.ril.commons.utils;

import org.junit.Assert;
import org.junit.Test;

public class NullAwareBeanUtilsTest {
  @Test
  public void copyProperties() throws Exception {
    Order order = new Order();
    order.setId(1L);
    order.setCost(20);

    Order order1 = new Order();
    order.setId(2L);

    NullAwareBeanUtils nullAwareBeanUtils = new NullAwareBeanUtils();
    nullAwareBeanUtils.copyProperties(order, order1);

    Assert.assertEquals(2L, (long) order.getId());
    Assert.assertEquals(20, (int) order.getCost());
  }
}

class Order {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  private Long id;
  private Integer cost;
}
