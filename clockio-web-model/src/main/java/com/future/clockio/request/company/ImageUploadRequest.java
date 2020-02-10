package com.future.clockio.request.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadRequest {
  private UUID employeeId;

  private String faceListId; // use as tag in cloudinary image (grouping)
  private MultipartFile file;
  private boolean persisted;
}
