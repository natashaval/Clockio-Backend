package com.future.clockio.request.company;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeCreateRequest {
  private String firstName;
  private String lastName;
  private String phone;
  private String email;

  private String photoUrl;
  private String faceListId;

//  private String branchId;
  private UUID departmentId;
}
