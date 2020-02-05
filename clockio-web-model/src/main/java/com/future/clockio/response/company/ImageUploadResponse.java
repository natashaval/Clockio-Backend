package com.future.clockio.response.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUploadResponse {
  @JsonProperty("public_id")
  private String publicId;

  private String format;

  @JsonProperty("resource_type")
  private String resourceType;
  private String url;
}
