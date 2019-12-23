package com.future.clockio.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
import com.future.clockio.entity.constant.EStatus;
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
@Table(name = DocumentName.EMPLOYEE)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Employee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String status;

  private String firstName;

  private String lastName;

  private String phone;

  private String email;

  private String profileUrl; // set profile picture Url from cloudinary

  private String faceListId;

  private double lastLatitude;
  private double lastLongitude;

  private Date lastCheckIn;
  private Date lastCheckOut;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Department department;
}
