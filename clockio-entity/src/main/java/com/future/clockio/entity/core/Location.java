package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.base.Coordinates;
import com.future.clockio.entity.company.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Location")
public class Location extends BaseEntity {
  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Employee employee;

//  private Coordinates coordinates;
  private double latitude;
  private double longitude;
}
