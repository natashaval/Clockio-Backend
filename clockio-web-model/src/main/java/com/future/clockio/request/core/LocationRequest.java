package com.future.clockio.request.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
  private UUID employeeId;
  private double latitude;
  private double longitude;
}
