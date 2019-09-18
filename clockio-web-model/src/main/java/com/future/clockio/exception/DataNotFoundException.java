package com.future.clockio.exception;

import lombok.Data;

@Data
public class DataNotFoundException extends DefaultRuntimeException {
  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException() {
  }
}