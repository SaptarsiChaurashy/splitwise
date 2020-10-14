package com.ril.gamification.service;

import com.google.gson.Gson;
import com.ril.gamification.dao.entity.GroupEntity;
import com.ril.gamification.dao.entity.UserEntity;
import com.ril.gamification.dao.repository.BillEntityRepository;
import com.ril.gamification.dao.repository.GroupEntityRepository;
import com.ril.gamification.dao.repository.UserEntityRespository;
import com.ril.gamification.engine.enums.ExpenseType;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.User;
import com.ril.gamification.engine.message.split.EqualSplit;
import com.ril.gamification.engine.message.split.ExactSplit;
import com.ril.gamification.engine.message.split.PercentSplit;
import com.ril.gamification.service.mapper.MapperFactory;
import com.ril.gamification.service.service.ExpenseManager;
import com.ril.gamification.service.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class ApplicationTest {

  @Autowired
  private UserEntityRespository userEntityRespository;
  @Autowired
  private ExpenseManager expenseManager;
  @Autowired
  private UserService userService;

  @Autowired
  private GroupEntityRepository groupEntityRepository;

  private final Gson gson = new Gson();

  @Test
  public void testme() {

    //save 5 users
    List<UserEntity> savedUsers = new ArrayList<>(createAndSaveUsers(Arrays.asList("u1", "u2", "u3", "u4", "u5")));
    UserEntity payer1 = createGroup("G1", new ArrayList<>(Arrays.asList(savedUsers.get(0), savedUsers.get(1), savedUsers.get(2))));
    UserEntity payer2 = createGroup("G2", new ArrayList<>(Arrays.asList(savedUsers.get(0), savedUsers.get(2))));
    UserEntity payer3 = createGroup("G3", new ArrayList<>(Arrays.asList(savedUsers.get(2), savedUsers.get(1), savedUsers.get(4))));
    assignBillToAGroup("G1", 300, ExpenseType.EQUAL, payer1.getName(), new ArrayList<>(Arrays.asList(savedUsers.get(0), savedUsers.get(1), savedUsers.get(2))), null);
    System.out.println("Payer : " + gson.toJson(payer1));
    System.out.println("==================================================================================");
    getAllUserDetails();
    System.out.println("==================================================================================");
    assignBillToAGroup("G2", 1200, ExpenseType.EXACT, payer2.getName(), new ArrayList<>(Arrays.asList(savedUsers.get(0), savedUsers.get(2))), new ArrayList<>(Arrays.asList(2, 1)));
    System.out.println("Payer : " + gson.toJson(payer2));
    System.out.println("==================================================================================");
    getAllUserDetails();
    System.out.println("==================================================================================");
    assignBillToAGroup("G3", 400, ExpenseType.PERCENT, payer3.getName(), new ArrayList<>(Arrays.asList(savedUsers.get(2), savedUsers.get(1), savedUsers.get(4))), new ArrayList<>(Arrays.asList(25, 25, 50)));
    System.out.println("Payer : " + gson.toJson(payer3));
    System.out.println("==================================================================================");
    getAllUserDetails();
    System.out.println("==================================================================================");
    System.out.println("==================================================================================");
    getUserDetailsOnGroupLevel(Group.builder().groupId(1L).name("G1").build(), MapperFactory.userMapper.convertToBO(savedUsers.get(1)));
    getUserDetailsOnGroupLevel(Group.builder().name("G1").groupId(1L).build(), MapperFactory.userMapper.convertToBO(savedUsers.get(2)));
    System.out.println("==================================================================================");
    System.out.println("==================================================================================");
    getUserDetailsOnGroupLevel(Group.builder().name("G2").groupId(2L).build(), MapperFactory.userMapper.convertToBO(savedUsers.get(0)));
    System.out.println("==================================================================================");
    System.out.println("==================================================================================");
    getUserDetailsOnGroupLevel(Group.builder().name("G3").groupId(3L).build(), MapperFactory.userMapper.convertToBO(savedUsers.get(4)));
    getUserDetailsOnGroupLevel(Group.builder().name("G3").groupId(3L).build(), MapperFactory.userMapper.convertToBO(savedUsers.get(2)));
    System.out.println("==================================================================================");
  }

  private void getUserDetailsOnGroupLevel(Group g1, User user) {
    userService.getUserAndGroupLevelDetails(user, g1);
  }

  private void getAllUserDetails() {

    final Iterable<UserEntity> all = userEntityRespository.findAll();
    all.forEach(userEntity -> userService.getUserLevelDetails(MapperFactory.userMapper.convertToBO(userEntity)));
  }

  private void assignBillToAGroup(String g1, double amount, ExpenseType expenseType, String paidBy, ArrayList<UserEntity> userEntityList, ArrayList<Integer> ratio) {
    Map<Long, Double> share = new HashMap<>();
    if (expenseType.equals(ExpenseType.EXACT)) {
      long sum = ratio.stream().mapToInt(integer -> integer).sum();
      for (int i = 0; i < userEntityList.size(); i++) {
        share.putIfAbsent(userEntityList.get(i).getId(), amount * ratio.get(i) / sum);
      }
    }
    if (expenseType.equals(ExpenseType.PERCENT)) {
      for (int i = 0; i < userEntityList.size(); i++) {
        share.putIfAbsent(userEntityList.get(i).getId(), (double) ratio.get(i));
      }
    }
    List<Split> splits = new ArrayList<>();
    userEntityList.forEach(userEntity -> {
      Split split;
      if (expenseType.equals(ExpenseType.EQUAL)) {
        split = new EqualSplit(null, null);
      } else if (expenseType.equals(ExpenseType.EXACT)) {
        split = new ExactSplit(null, null, share.get(userEntity.getId()));
      } else {
        split = new PercentSplit(null, null, share.get(userEntity.getId()));
      }
      split.setUserId(userEntity.getId());
      splits.add(split);
    });
    expenseManager.addGroupExpense(expenseType, amount, paidBy, splits, "Bill b1 created", g1);
  }

  private UserEntity createGroup(String g1, ArrayList<UserEntity> asList) {
    GroupEntity groupEntity = GroupEntity.builder().name(g1).users(new HashSet<>(asList)).build();
    groupEntity.setCreatedAt(new Date());
    groupEntity.setUpdatedAt(new Date());
    final GroupEntity savedEntity = groupEntityRepository.save(groupEntity);
    System.out.println("==================================================================================");
    System.out.println("Created group for " + g1 + " : " + gson.toJson(savedEntity));
    System.out.println("==================================================================================");
    groupEntityRepository.findAll();
    //return a random payer for a group
    return new ArrayList<UserEntity>(savedEntity.getUsers()).get(0);

  }

  private Set<UserEntity> createAndSaveUsers(List<String> asList) {
    final Set<UserEntity> collect = asList.stream().map(s -> UserEntity.builder().email(s).name(s).username(s).build())
        .peek(userEntity -> {
          userEntity.setCreatedAt(new Date());
          userEntity.setUpdatedAt(new Date());
        })
        .map(userEntity -> userEntityRespository.save(userEntity)).collect(Collectors.toSet());
    System.out.println("==================================================================================");
    userEntityRespository.findAll().forEach(userEntity -> System.out.println(gson.toJson(userEntity)));
    System.out.println("==================================================================================");
    return collect;
  }

}
