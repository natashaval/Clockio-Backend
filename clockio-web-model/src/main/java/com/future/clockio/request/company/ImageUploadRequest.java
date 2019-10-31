package com.future.clockio.request.company;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ImageUploadRequest {
  private UUID employeeId;

  private String faceListId; // use as tag in cloudinary image (grouping)
  private MultipartFile file;
  private boolean persisted;
}
