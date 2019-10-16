package com.future.clockio.entity.company;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.base.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Document(collection = DocumentName.BRANCH)
@Table(name = "Branch")
public class Branch extends BaseEntity {

  @Id
  private String id;

  private String name;

  private String city;

  private String country;

//  private Coordinates location;
  private double latitude;
  private double longitude;
}
