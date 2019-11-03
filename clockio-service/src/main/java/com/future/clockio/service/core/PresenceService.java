package com.future.clockio.service.core;

import com.future.clockio.entity.core.Presence;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;

public interface PresenceService {
  List<Presence> findAll();
  BaseResponse checkIn(PresenceRequest request);
  BaseResponse checkOut(PresenceRequest request);
}
