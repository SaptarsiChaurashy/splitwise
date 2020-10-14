package com.ril.commons.service;

import com.google.common.collect.Lists;
import com.ril.commons.dao.CrudDao;
import com.ril.commons.entity.BaseEntity;
import com.ril.commons.entity.CoreEntity;
import com.ril.commons.entity.EntityChangeListener;
import com.ril.commons.entity.EntityHelper;
import com.ril.commons.model.BusinessEntity;
import com.ril.commons.utils.NullAwareBeanUtils;
import com.ril.commons.utils.ObjectUtils;
import com.ril.commons.utils.Tuple;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface CrudBOService<Entity extends BaseEntity, BO extends BusinessEntity>
    extends CrudService<BO> {

  /**
   * @param id Long primary key to identify object
   * @return Optional @code BusinessEntity
   */
  @Override
  default Optional<BO> get(Long id) {
    return getDaoManager()
        .get(id)
        .filter(this::isNotSoftDeleted)
        .map(val -> getMapper().convertToBO(val));
  }
  /**
   * Returns all business entities
   *
   * @return Collection of BusinessEntities
   */
  @Override
  default List<BO> findAll() {
    return Lists.newArrayList(getDaoManager().findAll()).stream()
        .filter(this::isNotSoftDeleted)
        .map(entity -> getMapper().convertToBO(entity))
        .collect(Collectors.toList());
  }

  /**
   * Filters Collection of BusinessEntities by their primary ids
   *
   * @param ids Collection of primary ids
   * @return Collection of BusinessEntities
   */
  @Override
  default List<BO> findAll(Collection<Long> ids) {
    return Lists.newArrayList(getDaoManager().findAllById(ids)).stream()
        .filter(this::isNotSoftDeleted)
        .map(entity -> getMapper().convertToBO(entity))
        .collect(Collectors.toList());
  }

  /**
   * @param filterParams Multivalued fields to filter result
   * @param <S> An class extending BusinessEntity
   */
  @Override
  default <S extends BO> List<S> findAll(MultiValueMap<String, String> filterParams) {
    return null;
  }

  /**
   * @param data BusinessEntity to be updated
   * @return Updated BusinessEntity
   */
  // for update from non-null to null the null values are ignored
  @Override
  default BO update(BO data) {
    CrudDao<Entity, Long> dao = getDaoManager();
    return dao.get(Long.valueOf(data.getUniqueId()))
        .map(getMapper()::convertToBO)
        .map(
            old -> {
              BO newObj = ObjectUtils.deepCopy(old);
              NullAwareBeanUtils nullAwareBeanUtils = new NullAwareBeanUtils();
              nullAwareBeanUtils.copyProperties(newObj, data);
              return new Tuple<>(old, newObj);
            })
        .map(
            t -> {
              BO savedNew =
                  getMapper()
                      .convertToBO(getDaoManager().save(getMapper().convertToEntity(t.getRight())));
              changeListener().ifPresent(c -> c.accept(Optional.of(t.getLeft()), savedNew));
              return savedNew;
            })
        .orElseThrow(() -> new IllegalArgumentException("Bad Entity to update"));
  }

  /**
   * @param data BusinessEntity to be saved
   * @return saved BusinessEntity
   */
  @Override
  default BO save(BO data) {
    Optional<BO> oldOp = Optional.empty();
    if (data.getUniqueId() != null && !data.getUniqueId().equals("")) {
      oldOp = getDaoManager().get(Long.valueOf(data.getUniqueId())).map(getMapper()::convertToBO);
    }
    BO saved = getMapper().convertToBO(getDaoManager().save(getMapper().convertToEntity(data)));
    Optional<BO> finalOldOp = oldOp;
    changeListener().ifPresent(c -> c.accept(finalOldOp, saved));
    return saved;
  }

  @Override
  default void delete(Long id) {
    getDaoManager()
        .get(id)
        .ifPresent(
            entity -> {
              if (EntityHelper.isSoftDeletable(entity)) {
                ((CoreEntity) entity).setActive(false);
                getDaoManager().save(entity);
              } else {
                getDaoManager().delete(entity);
              }
            });
  }

  AbstractMappingConverter<BO, Entity> getMapper();
  /**
   * Returns DaoManager implementation for the given Entity Long is the primary key kept by default.
   *
   * @return Dao Manager class
   */
  CrudDao<Entity, Long> getDaoManager();

  default Optional<EntityChangeListener<BO>> changeListener() {
    return Optional.empty();
  }
}
