package com.future.clockio.request.company;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadRequest {
  private String employeeId;
  private String faceListId; // use as tag in cloudinary image (grouping)
  private MultipartFile file;
  private boolean persisted;
}
