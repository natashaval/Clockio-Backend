package com.future.clockio.helper;

import com.future.clockio.response.base.BaseResponse;
import org.springframework.http.HttpStatus;

public class ResponseHelper {
  public static BaseResponse toBaseResponse(HttpStatus httpStatus, Exception e) {
    BaseResponse response = new BaseResponse();
    response.setSuccess(!httpStatus.isError());
    response.setMessage(e.getMessage());
    return response;
  }

}
