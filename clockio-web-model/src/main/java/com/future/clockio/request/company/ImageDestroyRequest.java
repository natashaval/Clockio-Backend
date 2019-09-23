package com.future.clockio.request.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDestroyRequest {
  private String employeeId;
  private List<String> publicId;
  private String tag;
  private boolean byTag; // delete all resources by tag
}
