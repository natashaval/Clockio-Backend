package com.future.clockio.request.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

//http://joda-time.sourceforge.net/quickstart.html
//http://joda-time.sourceforge.net/apidocs/org/joda/time/DateMidnight.html
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationHistoryRequest {
  private UUID employeeId;
  private int year;
  private int month;
  private int day;
}
