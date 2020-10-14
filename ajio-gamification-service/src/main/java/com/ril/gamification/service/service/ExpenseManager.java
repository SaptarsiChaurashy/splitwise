package com.ril.gamification.service.service;

import com.google.gson.Gson;
import com.ril.commons.trywith.Try;
import com.ril.commons.utils.Applicatives;
import com.ril.commons.utils.Functors;
import com.ril.commons.utils.Tuple;
import com.ril.gamification.dao.entity.UserEntity;
import com.ril.gamification.dao.repository.BillEntityRepository;
import com.ril.gamification.dao.repository.UserEntityRespository;
import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.User;
import com.ril.gamification.service.mapper.MapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExpenseManager {

  private final Gson gson = new Gson();
  private final UserEntityRespository userEntityRespository;
  private final BillingService billingService;
  private final GroupService groupService;

  public void addGroupExpense(ExpenseType expenseType, double amount, String paidBy, List<Split> splits, String description, String group) {

    final Try<Tuple<UserEntity, Group>> validationTry =
        Applicatives.applicativeRun(Functors.Options2Try(userEntityRespository.findByName(paidBy),
            new IllegalArgumentException("User not present")),
            groupService.findByGroupName(group),
            ((userEntity1, group1) -> {
              if (!group1.getUsers().stream().map(User::getUserId).collect(Collectors.toSet())
                  .containsAll(splits.stream().map(Split::getUserId).collect(Collectors.toSet()))) {
                throw new IllegalArgumentException("User group is invalid");
              }
              if (!validatePaidBy(userEntity1, splits)) {
                throw new IllegalArgumentException("User not a part of the split");
              }
              return new Tuple(userEntity1, group1);
            }));

    if (validationTry.isFailure()) {
      return;
    }
    //TODO move it to user service
    final Set<UserEntity> byIdIn = userEntityRespository.findByIdIn(splits.stream().map(split -> split.getUserId()).collect(Collectors.toSet()));
    if (splits.size() != byIdIn.size()) {
      throw new IllegalArgumentException("All Split Users not present");
    }
    splits.forEach(split -> {
      split.setGroup(validationTry.get().getRight());
      split.setUser(MapperFactory.userMapper.convertToBO(byIdIn
          .stream()
          .filter(userEntity1 -> userEntity1.getId().equals(split.getUserId()))
          .findFirst()
          .get()));
    });
    Bill bill = BillingServiceHelper.createBill(expenseType, amount, validationTry.get().getLeft().getId(), new HashSet(splits), new Date(), description, validationTry.get().getRight());
    final Bill savedBill = billingService.save(bill);
    savedBill.getSplits().forEach(split -> System.out.println(gson.toJson(split)));
    return;
  }

  private boolean validatePaidBy(UserEntity userEntity1, List<Split> splits) {
    return splits.stream().anyMatch(split -> split.getUserId().equals(userEntity1.getId()));
  }

}
