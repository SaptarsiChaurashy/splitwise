package com.ril.commons.service;

import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
public abstract class AbstractMappingConverter<BO, E> {
  public abstract Function<BO, E> businessToEntity();

  public abstract Function<E, BO> entityToBusinessObject();

  public BO convertToBO(E entity) {
    return this.entityToBusinessObject().apply(entity);
  }

  public E convertToEntity(BO bo) {
    return this.businessToEntity().apply(bo);
  }
}
