package com.future.clockio.service.core;

import com.future.clockio.entity.core.Location;
import com.future.clockio.request.core.LocationHistoryRequest;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface LocationService {
  List<Location> findEmployeeLocation(LocationHistoryRequest request);

  BaseResponse addLocation(LocationRequest request);
  Location findById(UUID id);
}
