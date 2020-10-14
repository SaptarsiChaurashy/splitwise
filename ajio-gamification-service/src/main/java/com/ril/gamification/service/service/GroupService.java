package com.ril.gamification.service.service;

import com.ril.commons.dao.CrudDao;
import com.ril.commons.service.AbstractMappingConverter;
import com.ril.commons.service.CrudBOService;
import com.ril.commons.trywith.Try;
import com.ril.commons.utils.Functors;
import com.ril.gamification.dao.entity.GroupEntity;
import com.ril.gamification.dao.repository.GroupEntityRepository;
import com.ril.gamification.engine.message.Group;
import com.ril.gamification.service.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService implements CrudBOService<GroupEntity, Group> {

  private final GroupEntityRepository groupEntityRepository;
  private final GroupMapper groupMapper = new GroupMapper();

  @Override
  public AbstractMappingConverter<Group, GroupEntity> getMapper() {
    return groupMapper;
  }

  @Override
  public CrudDao<GroupEntity, Long> getDaoManager() {
    return groupEntityRepository;
  }

  public Try<Group> findByGroupName(String name) {

    return Functors.Options2Try(groupEntityRepository.findByName(name), new RuntimeException("No group found by the name"))
        .map(groupEntity -> getMapper().convertToBO(groupEntity));
  }
}
