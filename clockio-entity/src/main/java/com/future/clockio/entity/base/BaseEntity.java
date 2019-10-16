package com.future.clockio.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @JsonIgnore
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @JsonIgnore
  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updatedAt;

  @JsonIgnore
  @LastModifiedBy
  private String updatedBy;
}
