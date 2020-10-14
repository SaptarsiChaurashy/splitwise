package com.ril.gamification.engine.message.bills;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.split.EqualSplit;

import java.util.Date;
import java.util.Set;

public class EqualBill extends Bill {

  public EqualBill(Long billId, Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billId, billDate, description, amount, group, splits, paidBy);
  }

  public EqualBill(Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billDate, description, amount, group, splits, paidBy);
  }
  @Override
  @JsonIgnore
  public boolean validate() {
    for (Split split : getSplits()) {
      if (!(split instanceof EqualSplit)) {
        return false;
      }
    }

    return true;
  }
}
