package com.ril.gamification.engine.message;

import com.ril.gamification.engine.enums.ExpenseType;
import lombok.Data;

@Data
public abstract class Split {

  Long userId;
  User user;
  Double amount;
  ExpenseType expenseType;
  Group group;

  public Split(User user, Group group) {
    this.user = user;
    this.group = group;
    this.expenseType = getExpenseType();
  }

  public abstract ExpenseType getExpenseType();

}
