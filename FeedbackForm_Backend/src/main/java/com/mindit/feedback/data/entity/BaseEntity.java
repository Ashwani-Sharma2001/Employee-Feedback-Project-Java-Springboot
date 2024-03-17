package com.mindit.feedback.data.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

  @Basic(optional = true)
  @Column(name = "created_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Basic(optional = true)
  @Column(name = "created_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedAt;
}
