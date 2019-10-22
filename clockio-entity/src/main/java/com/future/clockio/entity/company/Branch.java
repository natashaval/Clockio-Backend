package com.future.clockio.entity.company;

import com.future.clockio.entity.base.BaseEntity;
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
//@Document(collection = DocumentName.BRANCH)
@Table(name = "Branch")
public class Branch extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  private String city;

  private String country;

//  private Coordinate location;
  private double latitude;
  private double longitude;
}
