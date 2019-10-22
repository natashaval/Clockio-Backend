package com.future.clockio.entity.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {

//  private String type;
//  private String coordinates;
  private double latitude;
  private double longitude;
}
