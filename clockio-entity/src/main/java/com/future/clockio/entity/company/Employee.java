package com.future.clockio.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
import com.future.clockio.entity.constant.Status;
import com.future.clockio.entity.base.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"target"})
@Document(collection = DocumentName.EMPLOYEE)
public class Employee extends BaseEntity {
  @Id
  private String id;

  private Status status;

  private String firstName;

  private String lastName;

  private String phone;

  private String email;

  private String profileUrl; // set profile picture Url from cloudinary

  private List<Photo> photoUrl = new ArrayList<>(); // list of trained face

  private String faceListId;

  private Coordinate lastCoordinate;

  private Date lastCheckIn;

  private Date lastCheckOut;

  @DBRef(lazy = true)
  private Branch branch;

  @DBRef(lazy = true)
  private Department department;
}
