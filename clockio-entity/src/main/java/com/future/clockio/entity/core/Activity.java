package com.future.clockio.entity.core;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DocumentName.ACTIVITY)
@JsonIgnoreProperties({"employee"})
public class Activity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Employee employee;

  private String title;

  private String content;

  private Date date;

  private String startTime;

  private String endTime;

  private double latitude;
  private double longitude;
}
