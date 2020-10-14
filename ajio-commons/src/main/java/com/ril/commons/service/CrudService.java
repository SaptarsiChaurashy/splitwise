package com.ril.commons.service;

import com.ril.commons.entity.CoreEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.Optional;

public interface CrudService<T> {

  /**
   * Optionally return Object if found else return empty optional
   *
   * @param id Long primary key to identify object
   * @return Optional Object
   */
  Optional<T> get(Long id);

  /**
   * Finds All the objects present
   *
   * @return Collection of all objects
   */
  Collection<T> findAll();

  /**
   * Finds all the objects with primary id in list of given ids
   *
   * @param ids Collection of primary ids
   * @return Collection of Objects
   */
  Collection<T> findAll(Collection<Long> ids);


  /**
   * find all objects which pass the given filter params
   *
   * @param filterParams Multivalued fields to filter result
   * @param <S> Any class extending T
   * @return Collection of objects
   */
  <S extends T> Collection<S> findAll(MultiValueMap<String, String> filterParams);

  /**
   * Updates Object. Primary id of object should be present.
   *
   * @param data Object to be updated
   * @return Object Updated
   */
  T update(T data);

  /**
   * Saves Object
   *
   * @param data Object to be saved
   * @return Object saved
   */
  T save(T data);

  /**
   *
   */
  void delete(Long id);

  default <Entity> boolean isNotSoftDeleted(Entity entity) {
    return !(entity instanceof CoreEntity) || ((CoreEntity) entity).isActive();
  }

}

