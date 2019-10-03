package com.future.clockio.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends DefaultRuntimeException {
  public InvalidRequestException(String message) {
    super(message);
  }

  public InvalidRequestException() {
  }
}
