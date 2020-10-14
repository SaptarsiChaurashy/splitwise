package com.ril.gamification.service.mapper;

import com.ril.commons.service.AbstractMappingConverter;
import com.ril.gamification.dao.entity.UserEntity;
import com.ril.gamification.engine.message.User;

import java.util.function.Function;

public class UserMapper extends AbstractMappingConverter<User, UserEntity> {
  @Override
  public Function<User, UserEntity> businessToEntity() {
    return bo -> {
      if (bo == null) {
        return null;
      }
      UserEntity userEntity = new UserEntity();
      userEntity.setId(bo.getUserId());
      userEntity.setUsername(bo.getUsername());
      userEntity.setEmail(bo.getEmail());
      userEntity.setName(bo.getName());
      return userEntity;
    };
  }

  @Override
  public Function<UserEntity, User> entityToBusinessObject() {
    return entity -> User.builder().email(entity.getEmail()).name(entity.getName()).userId(entity.getId()).username(entity.getUsername()).build();
  }
}
