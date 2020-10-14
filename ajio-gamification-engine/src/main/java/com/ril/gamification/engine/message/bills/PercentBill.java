package com.ril.gamification.engine.message.bills;

import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.split.PercentSplit;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
public class PercentBill extends Bill {

  public PercentBill(Long billId, Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billId, billDate, description, amount, group, splits, paidBy);
  }
  public PercentBill(Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    super(billDate, description, amount, group, splits, paidBy);
  }

  @Override
  public boolean validate() {
    for (Split split : getSplits()) {
      if (!(split instanceof PercentSplit)) {
        return false;
      }
    }

    double totalPercent = 100;
    double sumSplitPercent = 0;
    for (Split split : getSplits()) {
      PercentSplit exactSplit = (PercentSplit) split;
      sumSplitPercent += exactSplit.getPercent();
    }

    if (totalPercent != sumSplitPercent) {
      return false;
    }

    return true;
  }
}
