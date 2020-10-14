package com.ril.commons.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class CoreEntity extends BaseEntity {
  @Column protected boolean active = true;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected Date createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  protected Date updatedAt;

  @Column(name = "created_by", nullable = false)
  protected String createdBy = "";

  @Column(name = "updated_by")
  protected String updatedBy = "";
}
