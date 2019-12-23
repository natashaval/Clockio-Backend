package com.future.clockio.request.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StatusRequest {
  private UUID employeeId;
  private String status;
}
