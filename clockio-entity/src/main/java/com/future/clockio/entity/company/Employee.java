package com.future.clockio.entity.company;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
import com.future.clockio.entity.constant.Status;
import com.future.clockio.entity.base.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DocumentName.EMPLOYEE)
public class Employee extends BaseEntity {
  @Id
  private String id;

  private Status status;

  private String firstName;

  private String lastName;

  private String phone;

  private String email;

  private String photoUrl;

  private String faceListId;

  private Location lastLocation;

  private Date lastCheckIn;

  private Date lastCheckOut;

  @DBRef(lazy = true)
  private Branch branch;

  @DBRef(lazy = true)
  private Department department;
}
