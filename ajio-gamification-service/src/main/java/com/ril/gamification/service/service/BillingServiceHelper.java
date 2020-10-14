package com.ril.gamification.service.service;

import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.bills.EqualBill;
import com.ril.gamification.engine.message.bills.PercentBill;
import com.ril.gamification.engine.message.split.PercentSplit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class BillingServiceHelper {

  public static Bill createBill(ExpenseType expenseType, double amount, Long paidBy, Set<Split> splits, Date billDate, String description, Group group) {
    switch (expenseType) {
      case EXACT:
        setPaidBySplit(paidBy, amount, splits);
        return new EqualBill(billDate, description, amount, group, splits, paidBy);
      case PERCENT:
        for (Split split : splits) {
          PercentSplit percentSplit = (PercentSplit) split;
          split.setAmount((amount * percentSplit.getPercent()) / 100.0);
        }
        setPaidBySplit(paidBy, amount, splits);
        return new PercentBill(billDate, description, amount, group, splits, paidBy);
      case EQUAL:
        int totalSplits = splits.size();
        double splitAmount = ((double) Math.round(amount * 100 / totalSplits)) / 100.0;
        splits.forEach(split -> split.setAmount(splitAmount));
        new ArrayList<Split>(splits).get(0).setAmount(splitAmount + (amount - splitAmount * totalSplits));
        setPaidBySplit(paidBy, amount, splits);
        return new EqualBill(billDate, description, amount, group, splits, paidBy);
      default:
        throw new IllegalArgumentException("Not implemented");
    }
  }

  private static void setPaidBySplit(Long paidBy, double amount, Set<Split> splits) {
    splits.stream().filter(split -> split.getUserId().equals(paidBy)).forEach(split -> split.setAmount(split.getAmount() - amount));
  }
}
