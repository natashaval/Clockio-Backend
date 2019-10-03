package com.future.clockio.client.model.request;

import lombok.Data;

@Data
public class FindSimilarRequest {
  private String faceId;
  private String faceListId;
  private String mode = "matchFace";
}
