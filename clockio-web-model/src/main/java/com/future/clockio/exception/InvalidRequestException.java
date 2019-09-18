package com.future.clockio.exception;

import lombok.Data;

@Data
public class InvalidRequestException extends DefaultRuntimeException {
  public InvalidRequestException(String message) {
    super(message);
  }

  public InvalidRequestException() {
  }
}
