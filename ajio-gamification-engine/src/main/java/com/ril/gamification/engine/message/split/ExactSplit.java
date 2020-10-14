package com.ril.gamification.engine.message.split;

import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.User;

public class ExactSplit extends Split {


  public ExactSplit(User user, Group group, Double amount) {
    super(user,group);
    this.setAmount(amount);
  }

  @Override
  public ExpenseType getExpenseType() {
    return ExpenseType.EXACT;
  }
}
