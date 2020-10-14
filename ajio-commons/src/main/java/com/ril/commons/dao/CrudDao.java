package com.ril.commons.dao;

import com.ril.commons.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Optional;

public interface CrudDao<Entity extends BaseEntity, ID extends Serializable>
    extends CrudRepository<Entity, ID>, JpaSpecificationExecutor<Entity> {

  /**
   * Find entity by Primary key
   *
   * @param id Primary Id of the Entity
   * @return Optional of Entity if present, else Optional.empty()
   */
  default Optional<Entity> get(ID id) {
    return findById(id);
  }
}
