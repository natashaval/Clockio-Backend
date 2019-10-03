package com.future.clockio.service.company;

import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;

public interface ImageService {
  BaseResponse uploadImage(ImageUploadRequest request);

  BaseResponse destroyImage(ImageDestroyRequest request);
}
