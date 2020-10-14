package com.ril.gamification.dao.entity;

import com.ril.commons.entity.CoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_entity")
@Where(clause = "active= true")
@Audited
@AuditOverride(forClass = CoreEntity.class)
public class GroupEntity extends CoreEntity {

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @ToString.Exclude
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "group_user_mapping",
      joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> users;

  @OneToMany(mappedBy = "groupEntity", cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<BillEntity> billEntities = new HashSet<>();

  public void addBillEntity(BillEntity billEntity) {
    billEntities.add(billEntity);
    billEntity.setGroupEntity(this);
  }
}
