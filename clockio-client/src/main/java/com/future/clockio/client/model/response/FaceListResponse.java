package com.future.clockio.client.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceListResponse {
  private String faceListId;
  private String name;
  private String userData;
  private String recognitionModel;
  private List<Object> persistedFaces;
}
