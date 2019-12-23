package com.future.clockio.request.company;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageSaveRequest {
  private UUID employeeId;
  private String url;
  private String publicId;
}
