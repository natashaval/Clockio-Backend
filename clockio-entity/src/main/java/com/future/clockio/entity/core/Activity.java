package com.future.clockio.entity.core;


import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.company.Employee;
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
//@Document(collection = DocumentName.ACTIVITY)
@Entity
@Table(name = "Activity")
public class Activity extends BaseEntity {

  @Id
  private String Id;

//  @DBRef(lazy = true)
  @ManyToOne(fetch = FetchType.LAZY)
  private Employee employee;

  private String title;

  private String content;

  private Date date;

  private String startTime;

  private String endTime;

//  private Coordinate location;
  private double latitude;
  private double longitude;
}
