package com.ril.gamification.engine.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ril.commons.model.BusinessEntity;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public abstract class Bill extends BusinessEntity {

  private Long billId;
  private Date date;
  private String description;
  private Double amount;

  private Group group;
  private Set<Split> splits;
  private Long paidByUserId;

  public Bill(Long billId, Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    this.date = billDate;
    this.description = description;
    this.amount = amount;
    this.group = group;
    this.splits = splits;
    this.paidByUserId = paidBy;
    this.billId = billId;
  }

  public Bill(Date billDate, String description, double amount, Group group, Set<Split> splits, Long paidBy) {
    this.description = description;
    this.amount = amount;
    this.group = group;
    this.splits = splits;
    this.paidByUserId = paidBy;
    this.billId = billId;
  }


  @Override
  @JsonIgnore
  public String getUniqueId() {
    return this.billId == null ? null : this.billId.toString();
  }

  @JsonIgnore
  public abstract boolean validate();
}
