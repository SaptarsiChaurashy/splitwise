package com.ril.gamification.engine.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ril.commons.model.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
@EqualsAndHashCode(
    of = {"groupId", "name"},
    callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group extends BusinessEntity {

  private Long groupId;
  private String name;
  private Set<User> users;
  private Set<Bill> bills = new HashSet<>();

  @Override
  @JsonIgnore
  public String getUniqueId() {
    return this.groupId == null ? null : this.groupId.toString();
  }
}
