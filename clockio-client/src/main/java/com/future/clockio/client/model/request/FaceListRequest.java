package com.future.clockio.client.model.request;

import lombok.Data;

@Data
public class FaceListRequest {
  public String name;
  public String userData;
  public String recognitionModel = "recognition_02";
}
