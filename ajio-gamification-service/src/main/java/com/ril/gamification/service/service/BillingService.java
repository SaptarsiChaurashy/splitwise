package com.ril.gamification.service.service;

import com.ril.commons.dao.CrudDao;
import com.ril.commons.service.AbstractMappingConverter;
import com.ril.commons.service.CrudBOService;
import com.ril.commons.trywith.Try;
import com.ril.gamification.dao.entity.BillEntity;
import com.ril.gamification.dao.repository.BillEntityRepository;
import com.ril.gamification.engine.message.Bill;
import com.ril.gamification.service.mapper.BillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService implements CrudBOService<BillEntity, Bill> {

  private final BillEntityRepository billEntityRepository;
  private final BillMapper billMapper = new BillMapper();

  @Override
  public AbstractMappingConverter<Bill, BillEntity> getMapper() {
    return billMapper;
  }

  @Override
  public CrudDao<BillEntity, Long> getDaoManager() {
    return billEntityRepository;
  }

  public Try<Bill> saveABill(Bill bill) {
    return Try.with(() -> save(bill));
  }
}
