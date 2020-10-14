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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_entity")
@Where(clause = "active= true")
@Audited
@AuditOverride(forClass = CoreEntity.class)
public class UserEntity extends CoreEntity {

  @Column(name = "name", unique = true, nullable = false)
  private String name;
  @Column(name = "username", unique = true, nullable = false)
  private String username;
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @ToString.Exclude
  @ManyToMany(mappedBy = "users")
  private Set<GroupEntity> groupEntitySet = new HashSet<>();

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<SplitEntity> splitEntities;

}
