package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = DocumentName.WORK)
@Entity
@Table(name = "Work")
public class Work extends BaseEntity {
  // Work's Done Today

  @Id
  private String id;

//  @DBRef(lazy = true)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Employee employee;

  private Date checkIn;

  private Date checkOut;

}
