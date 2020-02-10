package com.future.clockio.request.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateRequest {
  private String firstName;
  private String lastName;
  private String phone;
  private String email;
  private String profileUrl;

  private UUID departmentId;
  private UUID userId;
}