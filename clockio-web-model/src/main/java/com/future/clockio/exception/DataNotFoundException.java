package com.future.clockio.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends DefaultRuntimeException {
  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException() {
  }
}
