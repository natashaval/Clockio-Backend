package com.future.clockio.service.core;

import com.future.clockio.entity.core.Presence;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface PresenceService {
  List<Presence> findAll();
  Presence findById(UUID id);
  BaseResponse checkIn(PresenceRequest request);
  BaseResponse checkOut(PresenceRequest request);
}
