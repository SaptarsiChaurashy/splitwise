package com.ril.gamification.dao.repository;

import com.ril.commons.dao.CrudDao;
import com.ril.gamification.dao.entity.UserEntity;

import java.util.Optional;
import java.util.Set;

public interface UserEntityRespository extends CrudDao<UserEntity, Long> {

  Optional<UserEntity> findByName(String name);

  Set<UserEntity> findByIdIn(Set<Long> ids);
}
