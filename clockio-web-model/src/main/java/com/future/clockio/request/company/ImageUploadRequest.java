package com.future.clockio.request.company;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequest {
  private String employeeId;
  private MultipartFile file;
}
