package com.ril.gamification.engine.message.split;

import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.User;

public class EqualSplit extends Split {


  public EqualSplit(User user, Group group) {
    super(user, group);
  }

  @Override
  public ExpenseType getExpenseType() {
    return ExpenseType.EQUAL;
  }
}
