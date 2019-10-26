package com.future.clockio.service.core;

import com.future.clockio.entity.core.Presence;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface PresenceService {
  BaseResponse checkInPhotoUpload(ImageUploadRequest request);

  List<Presence> findAll();
  BaseResponse checkIn(PresenceRequest request);
  BaseResponse checkOut(PresenceRequest request);
}
