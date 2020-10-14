package com.ril.gamification.dao.repository;

import com.ril.commons.dao.CrudDao;
import com.ril.gamification.dao.entity.GroupEntity;

import java.util.Optional;

public interface GroupEntityRepository extends CrudDao<GroupEntity, Long> {

  Optional<GroupEntity> findByName(String name);
}
