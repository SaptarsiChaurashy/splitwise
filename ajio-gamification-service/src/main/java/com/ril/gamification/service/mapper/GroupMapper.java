package com.ril.gamification.service.mapper;

import com.ril.commons.service.AbstractMappingConverter;
import com.ril.gamification.dao.entity.GroupEntity;
import com.ril.gamification.engine.message.Group;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupMapper extends AbstractMappingConverter<Group, GroupEntity> {
  @Override
  public Function<Group, GroupEntity> businessToEntity() {
    return bo -> {
      if (bo == null) {
        return null;
      }
      GroupEntity groupEntity = new GroupEntity();
      groupEntity.setId(bo.getGroupId());
      groupEntity.setName(bo.getName());
      groupEntity.setUsers(Optional.ofNullable(bo.getUsers()).orElseGet(HashSet::new).stream().map(user -> MapperFactory.userMapper.convertToEntity(user)).collect(Collectors.toSet()));
      Optional.ofNullable(bo.getBills()).orElseGet(HashSet::new).stream().map(bill -> MapperFactory.billMapper.convertToEntity(bill)).forEach(groupEntity::addBillEntity);
      return groupEntity;
    };
  }

  @Override
  public Function<GroupEntity, Group> entityToBusinessObject() {
    return entity -> Group.builder()
        .groupId(entity.getId())
        .bills(Optional.ofNullable(entity.getBillEntities()).orElseGet(HashSet::new).stream().map(billEntity -> MapperFactory.billMapper.convertToBO(billEntity)).collect(Collectors.toSet()))
        .name(entity.getName())
        .users(entity.getUsers().stream().map(userEntity -> MapperFactory.userMapper.convertToBO(userEntity)).collect(Collectors.toSet())).build();
  }
}
