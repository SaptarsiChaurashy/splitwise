package com.ril.gamification.service.mapper;

import com.ril.commons.service.AbstractMappingConverter;
import com.ril.gamification.dao.entity.SplitEntity;
import com.ril.gamification.engine.message.Split;
import com.ril.gamification.engine.message.split.EqualSplit;
import com.ril.gamification.engine.message.split.ExactSplit;
import com.ril.gamification.engine.message.split.PercentSplit;

import java.util.Date;
import java.util.function.Function;

public class SplitMapper extends AbstractMappingConverter<Split, SplitEntity> {
  @Override
  public Function<Split, SplitEntity> businessToEntity() {
    return bo -> {
      if (bo == null) {
        return null;
      }
      SplitEntity splitEntity = new SplitEntity();
      splitEntity.setAmount(bo.getAmount());
      splitEntity.setCreatedAt(new Date());
      splitEntity.setUpdatedAt(new Date());
      splitEntity.setExpenseType(bo.getExpenseType());
      splitEntity.setGroupEntity(MapperFactory.groupMapper.convertToEntity(bo.getGroup()));
      splitEntity.setUserEntity(MapperFactory.userMapper.convertToEntity(bo.getUser()));
      splitEntity.setPercent(bo instanceof PercentSplit ? ((PercentSplit) bo).getPercent() : null);
      return splitEntity;
    };

  }

  @Override
  public Function<SplitEntity, Split> entityToBusinessObject() {
    return entity -> {
      Split split;
      switch (entity.getExpenseType()) {
        case EQUAL:
          split = new EqualSplit(MapperFactory.userMapper.convertToBO(entity.getUserEntity()), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()));
          break;
        case EXACT:
          split = new ExactSplit(MapperFactory.userMapper.convertToBO(entity.getUserEntity()), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()), entity.getAmount());
          break;
        case PERCENT:
          split = new PercentSplit(MapperFactory.userMapper.convertToBO(entity.getUserEntity()), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()), entity.getPercent());
          break;
        default:
          throw new IllegalArgumentException("Not there!!!");
      }
      return split;
    };
  }
}
