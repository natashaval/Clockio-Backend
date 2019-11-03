package com.future.clockio.request.core;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

//http://joda-time.sourceforge.net/quickstart.html
//http://joda-time.sourceforge.net/apidocs/org/joda/time/DateMidnight.html
@Data
public class LocationHistoryRequest {
  private UUID employeeId;
  private int year;
  private int month;
  private int day;
}
