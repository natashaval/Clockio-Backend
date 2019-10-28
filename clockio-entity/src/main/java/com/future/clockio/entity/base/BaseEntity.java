package com.future.clockio.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//https://www.baeldung.com/database-auditing-jpa

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt", "createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class BaseEntity implements Serializable {

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @CreatedBy
  private String createdBy;

  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  @Column(name = "updated_at")
  private Date updatedAt;

  @LastModifiedBy
  private String updatedBy;
}
