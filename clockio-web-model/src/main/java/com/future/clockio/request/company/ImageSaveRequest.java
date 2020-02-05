package com.future.clockio.request.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageSaveRequest {
  private UUID employeeId;
  private String url;
  private String publicId;
}
