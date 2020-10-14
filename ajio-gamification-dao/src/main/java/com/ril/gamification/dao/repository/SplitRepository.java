package com.ril.gamification.dao.repository;

import com.ril.commons.dao.CrudDao;
import com.ril.gamification.dao.entity.GroupEntity;
import com.ril.gamification.dao.entity.SplitEntity;
import com.ril.gamification.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SplitRepository extends CrudDao<SplitEntity, Long> {

  @Query("SELECT SUM(s.amount) from SplitEntity s where s.groupEntity=:group and s.userEntity=:user")
  Double sumByGroupAndUser(@Param("group") GroupEntity group, @Param("user") UserEntity user);

  @Query("SELECT SUM(s.amount) from SplitEntity s where s.userEntity=:user")
  Double sumByUser(@Param("user") UserEntity user);
}
