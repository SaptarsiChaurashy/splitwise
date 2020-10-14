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
    of = {"userId", "name", "email"},
    callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BusinessEntity {

  private Long userId;
  private String name;
  private String username;
  private String email;

  private Set<Group> groupEntitySet = new HashSet<>();


  private Bill billEntity;

  @Override
  @JsonIgnore
  public String getUniqueId() {
    return this.userId == null ? null : this.userId.toString();
  }
}
