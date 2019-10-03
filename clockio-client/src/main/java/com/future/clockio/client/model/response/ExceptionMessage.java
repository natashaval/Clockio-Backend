package com.future.clockio.client.model.response;

import lombok.Data;

@Data
public class ExceptionMessage {
  private ErrorClient error;

  @Data
  public static class ErrorClient {
    private String code;
    private String message;
  }
}
