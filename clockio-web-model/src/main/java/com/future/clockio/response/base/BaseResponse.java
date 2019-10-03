package com.future.clockio.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

  private boolean success = false;
  private String message = "";
  private Map<String, String> details = new HashMap<>();

  public BaseResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public static BaseResponse success(String message) {
    return new BaseResponse(true, message);
  }

  public static BaseResponse failed(String message) {
    return new BaseResponse(false, message);
  }
}
