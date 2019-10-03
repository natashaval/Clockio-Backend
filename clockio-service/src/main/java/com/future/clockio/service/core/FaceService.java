package com.future.clockio.service.core;

import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FaceService {
  BaseResponse findSimilar(ImageUploadRequest request);
}
