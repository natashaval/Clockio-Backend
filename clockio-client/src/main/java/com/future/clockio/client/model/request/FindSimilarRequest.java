package com.future.clockio.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindSimilarRequest {
  private String faceId;
  private String faceListId;
  private String mode = "matchFace";
}
