package com.ril.gamification.engine.message.split;

import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class PercentSplit extends Split {

  double percent;

  public PercentSplit(User user, Group group, double percent) {
    super(user, group);
    this.percent = percent;
  }

  @Override
  public ExpenseType getExpenseType() {
    return ExpenseType.PERCENT;
  }
}
