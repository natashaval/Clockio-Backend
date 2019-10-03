package com.future.clockio.client.model.response;

import lombok.Data;

@Data
public class FindSimilarResponse {
  private String persistedFaceId;
  private Double confidence;
}
