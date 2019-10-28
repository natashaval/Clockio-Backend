package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DocumentName.LOCATION)
public class Location extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Employee employee;

  private double latitude;
  private double longitude;
}
