package com.future.clockio.request.core;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ActivityRequest {
  private UUID employeeId;
  private String title;
  private String content;
  private Date date;
  private String startTime;
  private String endTime;
  private double latitude;
  private double longitude;
}
