package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.Activity;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.core.ActivityRepository;
import com.future.clockio.request.core.ActivityRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

  @Autowired
  private ObjectMapper mapper;
  private ActivityRepository activityRepository;
  private EmployeeRepository employeeRepository;

  @Autowired
  public ActivityServiceImpl(ActivityRepository activityRepository, EmployeeRepository employeeRepository) {
    this.activityRepository = activityRepository;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public BaseResponse createActivity(ActivityRequest request) {
    Employee employee =
            employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new DataNotFoundException("Employee not found!"));
    Activity activity = mapper.convertValue(request, Activity.class);
    activity.setEmployee(employee);
    activityRepository.save(activity);
    return BaseResponse.success("Activity saved!");
  }

  @Override
  public BaseResponse updateActivity(UUID id, ActivityRequest request) {
    Optional.of(id)
            .map(activityRepository::findById)
            .orElseThrow(() -> new DataNotFoundException("Activity not found!"))
            .map(exist -> this.copyProperties(request, exist))
            .map(activityRepository::save);
    return BaseResponse.success("Activity updated!");
  }

  @Override
  public Page<Activity> findAllPageable(UUID employeeId, int page, int size) {
//    return activityRepository.findTop5ByEmployee_IdOrderByStartTimeDesc(employeeId);
    return activityRepository.findAllByEmployee_IdOrderByDateDesc(employeeId,
            PageRequest.of(page, size));
  }

  @Override
  public Page<Activity> findAll(int page, int size) {
    return activityRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,
            "date")));
  }

  @Override
  public BaseResponse deleteActivity(UUID id) {
    Activity activity =
            activityRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Activity" +
                    " not found!"));
    activityRepository.delete(activity);
    return BaseResponse.success("Activity deleted!");
  }

  private Activity copyProperties(ActivityRequest source, Activity target) {
    target.setTitle(source.getTitle());
    target.setContent(source.getContent());
    target.setStartTime(source.getStartTime());
    target.setEndTime(source.getEndTime());
    return target;
  }
}
