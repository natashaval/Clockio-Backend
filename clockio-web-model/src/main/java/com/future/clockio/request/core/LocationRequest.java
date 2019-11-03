package com.future.clockio.request.core;

import lombok.Data;

import java.util.UUID;

@Data
public class LocationRequest {
  private UUID employeeId;
  private double latitude;
  private double longitude;
}
