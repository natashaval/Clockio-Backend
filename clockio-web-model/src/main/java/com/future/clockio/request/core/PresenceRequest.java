package com.future.clockio.request.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class PresenceRequest {
  private UUID employeeId; // used when checkin
  private UUID presenceId; // used when checkout

  private String url; // photoUrl

  private Date checkIn;
  private Date checkOut;
  private double latitude;
  private double longitude;
}
