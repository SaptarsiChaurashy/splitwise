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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill_entity")
@Where(clause = "active= true")
@Audited
@AuditOverride(forClass = CoreEntity.class)
public class BillEntity extends CoreEntity {

  private Date date;
  private String description;
  private Double amount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "group_id")
  @ToString.Exclude
  private GroupEntity groupEntity;

  @OneToMany(mappedBy = "billEntity", cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<SplitEntity> splitEntities = new HashSet<>();

  @Column(name = "paid_by_user_id")
  private Long paidByUserId;

  public void addSplitEntity(SplitEntity splitEntity) {
    splitEntities.add(splitEntity);
    splitEntity.setBillEntity(this);
  }
}
