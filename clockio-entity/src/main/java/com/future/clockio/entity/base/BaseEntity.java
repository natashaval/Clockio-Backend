package com.future.clockio.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

  @CreatedDate
  private Date createdAt;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private Date updatedAt;

  @LastModifiedBy
  private String updatedBy;
}
