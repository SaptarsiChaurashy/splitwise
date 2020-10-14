package com.ril.gamification.dao.entity;

import com.ril.commons.entity.CoreEntity;
import com.ril.gamification.engine.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "split_entity")
@Where(clause = "active= true")
@Audited
@AuditOverride(forClass = CoreEntity.class)
public class SplitEntity extends CoreEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private UserEntity userEntity;
  private Double amount;
  private Double percent;

  @Enumerated(EnumType.STRING)
  private ExpenseType expenseType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  @ToString.Exclude
  private GroupEntity groupEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bill_id")
  @ToString.Exclude
  private BillEntity billEntity;
}
