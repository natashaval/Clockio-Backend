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
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DocumentName.PRESENCE)
public class Presence {
  // Presence's Done Today
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Employee employee;

  private Date checkIn;
  private Date checkOut;

  private double latitude;
  private double longitude;

}
