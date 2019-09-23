package com.future.clockio.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.client.model.response.ExceptionMessage;
import com.future.clockio.client.model.exception.RateLimitException;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;

//http://www.appsdeveloperblog.com/feign-error-handling-with-errordecoder/
//https://stackoverflow.com/questions/53525228/feign-errordecoder-retrieve-the-original-message

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder errorDecoder = new Default();

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public Exception decode(String s, Response response) {

    ExceptionMessage message = new ExceptionMessage();
    Reader reader = null;

    try {
      reader = response.body().asReader();
      String result = IOUtils.toString(reader);
      message  = mapper.readValue( result, ExceptionMessage.class);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    log.error("MESSAGE" + message.getError().getCode().toUpperCase() +
            "--" + message.getError().getMessage());

    switch (response.status()) {
      case 404:
        return new DataNotFoundException(message.getError().getCode().toUpperCase() +
                "--" + message.getError().getMessage());
      case 429:
        return new RateLimitException(message.getError().getCode().toUpperCase() +
                "--" + message.getError().getMessage());
      default:
        return new InvalidRequestException(message.getError().getCode().toUpperCase() +
                "--" + message.getError().getMessage());
    }

//    return errorDecoder.decode(s, response);
  }
}
