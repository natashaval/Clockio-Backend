package com.future.clockio.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddFaceResponse {
  private String persistedFaceId;
  private String faceId;
  private String recognitionModel;
}
