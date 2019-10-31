package com.future.clockio.request.core;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PresenceRequest {
  private UUID employeeId; // used when checkin
  private UUID presenceId; // used when checkout

  private String url; // photoUrl

  private Date checkIn;
  private Date checkOut;
  private double latitude;
  private double longitude;
}
