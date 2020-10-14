package com.ril.gamification.engine.message.bills;

import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.split.ExactSplit;

import java.util.Date;
import java.util.Set;

public class ExactBill extends Bill {

  public ExactBill(Long billId, Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billId, billDate, description, amount, group, splits, paidBy);
  }

  public ExactBill(Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billDate, description, amount, group, splits, paidBy);
  }

  @Override
  public boolean validate() {

    for (Split split : getSplits()) {
      if (!(split instanceof ExactSplit)) {
        return false;
      }
    }

    double totalAmount = getAmount();
    double sumSplitAmount = 0;
    for (Split split : getSplits()) {
      ExactSplit exactSplit = (ExactSplit) split;
      sumSplitAmount += exactSplit.getAmount();
    }

    if (totalAmount != sumSplitAmount) {
      return false;
    }

    return true;
  }
}
