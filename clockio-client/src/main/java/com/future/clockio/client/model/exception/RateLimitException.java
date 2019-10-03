package com.future.clockio.client.model.exception;

import com.future.clockio.exception.DefaultRuntimeException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends DefaultRuntimeException {
  public RateLimitException() {
  }

  public RateLimitException(String message) {
    super(message);
  }
}
