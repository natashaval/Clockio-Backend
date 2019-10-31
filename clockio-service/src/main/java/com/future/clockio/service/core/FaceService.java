package com.future.clockio.service.core;

import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FaceService {
  BaseResponse findSimilar(ImageUploadRequest request);
  BaseResponse uploadImage(ImageUploadRequest request);
  BaseResponse deleteImage(ImageDestroyRequest request);

  String faceDetect(String url);
  boolean checkSimilarity(String faceId, String faceListId);
}
