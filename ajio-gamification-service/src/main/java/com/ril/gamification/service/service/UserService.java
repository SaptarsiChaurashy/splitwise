package com.ril.gamification.service.service;

import com.ril.commons.dao.CrudDao;
import com.ril.commons.service.AbstractMappingConverter;
import com.ril.commons.service.CrudBOService;
import com.ril.gamification.dao.entity.UserEntity;
import com.ril.gamification.dao.repository.SplitRepository;
import com.ril.gamification.dao.repository.UserEntityRespository;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.engine.message.User;
import com.ril.gamification.service.mapper.MapperFactory;
import com.ril.gamification.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CrudBOService<UserEntity, User> {

  private final UserEntityRespository userEntityRespository;
  private final UserMapper userMapper = new UserMapper();
  private final SplitRepository splitRepository;

  @Override
  public AbstractMappingConverter<User, UserEntity> getMapper() {
    return userMapper;
  }

  @Override
  public CrudDao<UserEntity, Long> getDaoManager() {
    return userEntityRespository;
  }

  public void getUserAndGroupLevelDetails(User user, Group group) {

    final Double aDouble = splitRepository.sumByGroupAndUser(MapperFactory.groupMapper.convertToEntity(group), getMapper().convertToEntity(user));
    if (aDouble == null) {
      System.out.println("User " + user.getName() + " gets or owes nothing from group " + group.getName());
      return;
    }
    if (aDouble < 0) {
      System.out.println("User " + user.getName() + " gets " + Math.abs(aDouble) + " from group " + group.getName());
    } else {
      System.out.println("User " + user.getName() + " owes " + Math.abs(aDouble) + " to group " + group.getName());
    }
  }

  public void getUserLevelDetails(User user) {

    final Double aDouble = splitRepository.sumByUser(getMapper().convertToEntity(user));
    if (aDouble == null) {
      System.out.println("User " + user.getName() + " gets or owes nothing ");
      return;
    }
    if (aDouble < 0) {
      System.out.println("User " + user.getName() + " gets " + Math.abs(aDouble));
    } else {
      System.out.println("User " + user.getName() + " owes " + Math.abs(aDouble));
    }
  }
}
