package com.future.clockio.service.core;

import com.future.clockio.entity.core.Activity;
import com.future.clockio.request.core.ActivityRequest;
import com.future.clockio.response.base.BaseResponse;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ActivityService {
  BaseResponse createActivity(ActivityRequest request);
  BaseResponse updateActivity(UUID id, ActivityRequest request);
  Page<Activity> findAllPageable(UUID employeeId, int page, int size);
  List<Activity> findByEmployeeAndDateToday(UUID employeeId, Date date);
  Page<Activity> findByEmployeeAndDateBetween(UUID employeeId, Date start, Date end, int page,
                                              int size);
  Page<Activity> findAll(int page, int size);
  BaseResponse deleteActivity(UUID id);
}
