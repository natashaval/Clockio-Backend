package com.future.clockio.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindSimilarResponse {
  private String persistedFaceId;
  private Double confidence;
}
