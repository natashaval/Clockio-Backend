package com.future.clockio.controlleradvice;

import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.helper.ResponseHelper;
import com.future.clockio.response.base.BaseResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@Slf4j
public class ExceptionController {

  @ExceptionHandler(DataNotFoundException.class)
  @RequestMapping(produces = "application/vnd.error+json")
  public ResponseEntity<BaseResponse> dataNotFoundExceptionHandler(DataNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    log.error("Data not found!", ex);
    return new ResponseEntity<>(ResponseHelper.toBaseResponse(status, ex), status);
  }

  @ExceptionHandler(InvalidRequestException.class)
  @RequestMapping(produces = "application/vnd.error+json")
  public ResponseEntity<BaseResponse> invalidRequestExceptionHandler(InvalidRequestException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    log.error("Bad request!", ex);
    return new ResponseEntity<>(ResponseHelper.toBaseResponse(status, ex), status);
  }
}
