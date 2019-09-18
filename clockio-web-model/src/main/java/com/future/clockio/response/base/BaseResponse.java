package com.future.clockio.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

  private boolean success = false;
  private String message = "";

  public static BaseResponse success(String message) {
    return new BaseResponse(true, message);
  }

  public static BaseResponse failed(String message) {
    return new BaseResponse(false, message);
  }
}
