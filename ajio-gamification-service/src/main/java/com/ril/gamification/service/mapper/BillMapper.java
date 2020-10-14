package com.ril.gamification.service.mapper;

import com.ril.commons.service.AbstractMappingConverter;
import com.ril.gamification.dao.entity.BillEntity;
import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.engine.message.bills.EqualBill;
import com.ril.gamification.engine.message.bills.ExactBill;
import com.ril.gamification.engine.message.bills.PercentBill;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BillMapper extends AbstractMappingConverter<Bill, BillEntity> {
  @Override
  public Function<Bill, BillEntity> businessToEntity() {

    return bo -> {
      if (bo == null) {
        return null;
      }
      BillEntity billEntity = new BillEntity();
      billEntity.setAmount(bo.getAmount());
      billEntity.setDescription(bo.getDescription());
      billEntity.setDate(bo.getDate());
      billEntity.setCreatedAt(new Date());
      billEntity.setUpdatedAt(new Date());
      billEntity.setGroupEntity(MapperFactory.groupMapper.convertToEntity(bo.getGroup()));
      billEntity.setGroupEntity(MapperFactory.groupMapper.convertToEntity(bo.getGroup()));
      Optional.ofNullable(bo.getSplits()).orElseGet(HashSet::new).stream()
          .map(
              split ->
                  MapperFactory.splitMapper.convertToEntity(split))
          .forEach(billEntity::addSplitEntity);
      billEntity.setPaidByUserId(bo.getPaidByUserId());
      return billEntity;
    };
  }

  @Override
  public Function<BillEntity, Bill> entityToBusinessObject() {
    return entity -> {
      Bill bill;
      switch (entity.getSplitEntities().iterator().next().getExpenseType()) {
        case PERCENT:
          bill = new PercentBill(entity.getId(), entity.getDate(),
              entity.getDescription(),
              entity.getAmount(), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()),
              Optional.ofNullable(entity.getSplitEntities()).orElseGet(HashSet::new).stream().map(splitEntity -> MapperFactory.splitMapper.convertToBO(splitEntity)).collect(Collectors.toSet()),
              entity.getPaidByUserId());
          break;
        case EXACT:
          bill = new ExactBill(entity.getId(), entity.getDate(), entity.getDescription(),
              entity.getAmount(), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()),
              Optional.ofNullable(entity.getSplitEntities()).orElseGet(HashSet::new).stream().map(splitEntity -> MapperFactory.splitMapper.convertToBO(splitEntity)).collect(Collectors.toSet()),
              entity.getPaidByUserId());
          break;
        case EQUAL:
          bill = new EqualBill(entity.getId(), entity.getDate(), entity.getDescription(),
              entity.getAmount(), MapperFactory.groupMapper.convertToBO(entity.getGroupEntity()),
              Optional.ofNullable(entity.getSplitEntities()).orElseGet(HashSet::new).stream().map(splitEntity -> MapperFactory.splitMapper.convertToBO(splitEntity)).collect(Collectors.toSet()),
              entity.getPaidByUserId());
          break;
        default:
          throw new RuntimeException("Not implemented");
      }
      return bill;
    };
  }
}