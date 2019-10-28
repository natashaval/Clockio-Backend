package com.future.clockio.request.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDestroyRequest {
  private UUID employeeId;
  private List<String> publicId;
  private String tag;
  private boolean byTag; // delete all resources by tag
}
