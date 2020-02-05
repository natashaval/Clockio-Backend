package com.future.clockio.request.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDestroyRequest {
  private UUID employeeId;
  private List<String> publicId;
  private String tag;
  private boolean byTag; // delete all resources by tag
}
